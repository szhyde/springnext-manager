/*
 * 修改单号： 修改内容：
 */

package org.springnext.manager.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration  
public class ShiroConfiguration {  
      
        private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();  
      
        @Bean(name = "kitweeRealm")  
        public UserRealm getShiroRealm() {  
            return new UserRealm();  
        }  
      
        @Bean(name = "shiroEhcacheManager")  
        public EhCacheManager getEhCacheManager() {  
            EhCacheManager em = new EhCacheManager();  
            em.setCacheManagerConfigFile("classpath:cache/ehcache-shiro.xml");  
            return em;  
        }  
      
        @Bean(name = "lifecycleBeanPostProcessor")  
        public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {  
            return new LifecycleBeanPostProcessor();  
        }  
      
        @Bean  
        public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {  
            DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();  
            daap.setProxyTargetClass(true);  
            return daap;  
        }  
      
        @Bean(name = "securityManager")  
        public DefaultWebSecurityManager getDefaultWebSecurityManager() {  
            DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();  
            dwsm.setRealm(getShiroRealm());  
            dwsm.setCacheManager(getEhCacheManager());  
            return dwsm;  
        }  
      
        @Bean  
        public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {  
            AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();  
            aasa.setSecurityManager(getDefaultWebSecurityManager());  
            return new AuthorizationAttributeSourceAdvisor();  
        }  
      
        @Bean(name = "shiroFilter")  
        public ShiroFilterFactoryBean getShiroFilterFactoryBean() {  
            ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();  
            shiroFilterFactoryBean  
                    .setSecurityManager(getDefaultWebSecurityManager());  
            shiroFilterFactoryBean.setLoginUrl("/login/view");  
            shiroFilterFactoryBean.setSuccessUrl("/main/index");  
            //filterChainDefinitionMap.put("/**", "authc");  
            filterChainDefinitionMap.put("/login/**", "anon");  
            shiroFilterFactoryBean  
                    .setFilterChainDefinitionMap(filterChainDefinitionMap);  
            return shiroFilterFactoryBean;  
        }  
        
        @Bean
        public ShiroDialect shiroDialect() {
            return new ShiroDialect();
        }
}
