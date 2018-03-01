package com.example.lcdemo.config;//package com.itspeed.higu.config;
//
//import net.sf.ehcache.CacheManager;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.ehcache.EhCacheCacheManager;
//import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//
///**
// * Created by tsy
// */
//@Configuration
//@EnableCaching
//public class EhcacheConfig {
//    /**
//     * EhCache的配置
//     */
//    @Bean
//    public EhCacheCacheManager cacheManager(CacheManager cacheManager) {
//        return new EhCacheCacheManager(cacheManager);
//    }
//
//    /**
//     * EhCache的配置
//     */
//    @Bean
//    public EhCacheManagerFactoryBean ehcache() {
//        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
//        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
//        return ehCacheManagerFactoryBean;
//    }
//}
