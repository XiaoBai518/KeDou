package com.kedou.util;

import javax.annotation.Resource;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

@Component
public class ShiroClearCacheUtil {

	@Resource(name="springcacheManager")
	private EhCacheCacheManager cacheManager;
	
	/**
     * 更新用户授权信息缓存.
     */
    public  void clearCachedAuthorizationInfo(String username) {
        Cache<Object, AuthorizationInfo> cache = (Cache<Object, AuthorizationInfo>)cacheManager.getCache("authorizationCache");  
        cache.remove(username); 
    }

    /**
     * 清除所有用户授权信息缓存.
     */
    public  void clearAllCachedAuthorizationInfo() {
    	System.out.println(cacheManager);
    	System.out.println(cacheManager.getCache("authorizationCache"));
        Cache<Object, AuthorizationInfo> cache = (Cache<Object, AuthorizationInfo>)cacheManager.getCache("authorizationCache");  
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }
	
}
