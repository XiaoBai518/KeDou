package com.kedou.interceptor;

import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityListeners;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.service.visitNumber.VisitNumberServiceImpl;
@Controller
public class AfterStartLoad implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private EhCacheCacheManager cacheManager;
	@Autowired
	private VisitNumberServiceImpl visitNumberServiceImpl;


	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("初始化");
	             //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。  
				Cache cache = cacheManager.getCache("visitNumberCache");
				ValueWrapper value = cache.get("visitNumberMap");
				
				if(value==null) {
					try {
						cache.put("visitNumberMap", this.visitNumberServiceImpl.initMap());
					} catch (Exception e) {
						e.printStackTrace();
					}
					value = cache.get("visitNumberMap");
				}	
	}
}
