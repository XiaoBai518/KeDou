package com.kedou.interceptor;

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
import com.kedou.service.visitNumber.VisitNumberServiceImpl;


public class VisitNumberInterceptor implements HandlerInterceptor {


	@Resource(name="springcacheManager")
	private EhCacheCacheManager m2;

	@Resource
	private VisitNumberServiceImpl visitNumberServiceImpl;


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		System.out.println("浏览量拦截器开始");
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.getSession().getAttribute("loginUser")!=null) {
			Cache cache = m2.getCache("visitNumberCache");
			ValueWrapper value = cache.get("visitNumberMap");
			
			if(value==null) {
				cache.put("visitNumberMap", this.visitNumberServiceImpl.initMap());
				value = cache.get("visitNumberMap");
			}
			
			Map<Integer,BusinessVisitNumber> visitNumberMap = (Map<Integer,BusinessVisitNumber>)value.get();
			
			synchronized (visitNumberMap) {
				int busid = Integer.parseInt(request.getParameter("businessId"));
				BusinessVisitNumber bvn =  visitNumberMap.get(busid);
				//设置浏览量+1
				bvn.setVisitNumber(bvn.getVisitNumber()+1);
				System.out.println("浏览量为："+visitNumberMap.get(busid).getVisitNumber());
				cache.put("visitNumberMap", visitNumberMap);
			}
		}
		
		System.out.println("浏览量拦截器结束");
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {}


}
