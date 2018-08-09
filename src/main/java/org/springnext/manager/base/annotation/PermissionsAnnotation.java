package org.springnext.manager.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface PermissionsAnnotation {

	/**
	 * 权限，保存用英文字符，系统时使用
	 * 
	 * @return
	 */
	String permission();

	/**
	 * 权限描述
	 * 
	 * @return
	 */
	String permissionRemark() default "";
	
	/**
	 * 父级权限ID,不填表示没有父级权限
	 * 
	 * @return
	 */
	String parentPermission() default "";


	/**
	 * 资源URL
	 * 
	 * @return
	 */
	String url();

	/**
	 * 资源描述
	 * 
	 * @return
	 */
	String resourceRemark() default "";

}
