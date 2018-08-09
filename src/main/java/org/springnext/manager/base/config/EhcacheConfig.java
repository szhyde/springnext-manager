package org.springnext.manager.base.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import net.sf.ehcache.Ehcache;

@Configuration
@EnableCaching
public class EhcacheConfig {

	@Bean(name = "dictionaryEhcache")
	public Ehcache dictionaryEhcache() {
		return ehCacheCacheManager().getCacheManager().getEhcache("dictionary");
	}

	/*
	 * ehcache 主要的管理器
	 */
	@Bean(name = "ehCacheCacheManager")
	public EhCacheCacheManager ehCacheCacheManager() {
		return new EhCacheCacheManager(ehCacheManagerFactoryBean().getObject());
	}

	/*
	 * 据shared与否的设置,Spring分别通过CacheManager.create()或new
	 * CacheManager()方式来创建一个ehcache基地.
	 */
	@Bean(name = "ehCacheManagerFactoryBean")
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
		EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("cache/business_cache.xml"));
		return ehCacheManagerFactoryBean;
	}
}
