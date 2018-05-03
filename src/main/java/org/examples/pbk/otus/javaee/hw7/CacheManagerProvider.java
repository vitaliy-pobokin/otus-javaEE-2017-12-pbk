package org.examples.pbk.otus.javaee.hw7;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import java.util.List;

public class CacheManagerProvider {

    private static final String EMPLOYEE_CACHE = "filterEmployee";
    private static CacheManager cacheManager;

    public static void init() {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache(EMPLOYEE_CACHE,
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, List.class, ResourcePoolsBuilder.heap(100)))
                .build();
        cacheManager.init();
    }

    public static void dispose() {
        cacheManager.removeCache(EMPLOYEE_CACHE);
        cacheManager.close();
    }

    public static CacheManager getCacheManager() {
        return cacheManager;
    }
}
