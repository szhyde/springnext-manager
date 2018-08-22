package org.springnext.manager.base.annotation;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springnext.manager.base.entity.Permissions;
import org.springnext.manager.base.entity.Resources;
import org.springnext.manager.base.entity.Role;
import org.springnext.manager.base.service.PermissionsService;
import org.springnext.manager.base.service.ResourcesService;
import org.springnext.manager.base.service.RoleService;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 权限注解扫描入库
 * 
 * @author hyde
 *
 */
@Component
public class PermissionsScannerProcessor implements ApplicationContextAware {

	@Autowired
	private PermissionsService permissionsService;

	@Autowired
	private ResourcesService resourcesService;

	@Autowired
	private RoleService roleService;

	@Override
	@Transactional
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// 查找bean的范围限定在Controller层
		String[] beanNames = applicationContext.getBeanNamesForAnnotation(Controller.class);
		Queue<PermissionsAnnotation> queue = new LinkedList<PermissionsAnnotation>();
		for (String beanName : beanNames) {
			// 查询Bean内部的所有方法
			Method[] methods = applicationContext.getBean(beanName).getClass().getDeclaredMethods();
			for (Method method : methods) {
				// 判断该方法是否有PermissionsAnnotation注解
				if (method.isAnnotationPresent(PermissionsAnnotation.class)) {
					queue.offer(method.getAnnotation(PermissionsAnnotation.class));
				}
			}
		}
		PermissionsAnnotation pa;
		Resources resource;
		Permissions permission;
		Permissions parent;
		Map<String,Permissions> permissionsMap = Maps.newHashMap();
		// 开始对找到的资源进行入库操作
		while (queue.size() > 0) {
			pa = queue.poll();
			parent = null;
			if (StringUtils.isNotBlank(pa.parentPermission())) {
				parent = permissionsService.findOneByAttributesName("EQ_permissions", pa.parentPermission());
			}
			// 如果权限没有父级，或数据库中能找到父级，就开始入库，避免因为父级权限外键影响入库
			if (StringUtils.isBlank(pa.parentPermission()) || parent != null) {
				// 权限表
				permission = permissionsService.findOneByAttributesName("EQ_permissions", pa.permission());
				if (permission == null) {// permission表新增
					permission = new Permissions();
					permission.setPermissions(pa.permission());
					permission.setRemark(pa.permissionRemark());
					permission.setParent(parent);
					permission = permissionsService.save(permission);
					permissionsMap.put(permission.getTid(), permission);
				} else {// permission表修改
					permission.setPermissions(pa.permission());
					permission.setRemark(pa.permissionRemark());
					permission.setParent(parent);
					permission = permissionsService.save(permission);
					permissionsMap.put(permission.getTid(), permission);
				}

				resource = resourcesService.findOneByAttributesName("EQ_url", pa.url());
				// 资源表
				if (resource == null) {// resource表新增
					resource = new Resources();
					resource.setPermissionsName(pa.permission());
					resource.setUrl(pa.url());
					resource.setRemark(pa.resourceRemark());
					resource.setPermissions(permission);
					resourcesService.save(resource);
				} else {// resource表修改
					resource.setPermissionsName(pa.permission());
					resource.setUrl(pa.url());
					resource.setRemark(pa.resourceRemark());
					resource.setPermissions(permission);
					resourcesService.save(resource);
				}
			} else {
				queue.offer(pa);
			}
		}

		Role role = roleService.findOne("692dfb7178b842f09044bb86f34aaa3c");
		if (role != null) {
			List<Permissions> list = Lists.newArrayList();
			list.addAll(permissionsMap.values());
			role.setPermissions(list);
			roleService.save(role);
		}
	}

}
