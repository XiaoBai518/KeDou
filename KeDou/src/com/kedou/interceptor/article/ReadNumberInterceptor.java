package com.kedou.interceptor.article;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.kedou.entity.BusinessVisitNumber;
import com.kedou.service.forum.ArticleServiceImpl;
import com.kedou.service.visitNumber.VisitNumberServiceImpl;

public class ReadNumberInterceptor implements HandlerInterceptor{

	@Resource(name="springcacheManager")
	private EhCacheCacheManager cacheManager;

	@Resource
	private ArticleServiceImpl articleServiceImpl;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		System.out.println("文章浏览量拦截器开始");
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.getSession().getAttribute("loginUser")!=null) {
			Cache cache = cacheManager.getCache("articleReadNumberCache");
			ValueWrapper value = cache.get("readNumberMap");
			
			if(value==null) {
				cache.put("readNumberMap", this.articleServiceImpl.initMap());
				value = cache.get("readNumberMap");
			}
			
			Map<Integer,Integer> readNumberMap = (Map<Integer,Integer>)value.get();
			
			synchronized (readNumberMap) {
				int id = Integer.parseInt(request.getParameter("keyid"));
				readNumberMap.put(id, readNumberMap.get(id)+1);
				//设置文章浏览量+1
				cache.put("visitNumberMap", readNumberMap);
			}
		}
		
		System.out.println("文章浏览量拦截器结束");
		return true;
	}
	
	
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {}
}
