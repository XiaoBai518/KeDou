package com.kedou.service.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.kedou.entity.User;
import com.kedou.util.MD5;
import com.kedou.util.RandomValidateCode;

@Service
public class CommonServiceImpl {
	
	
	/**
	 * 生成验证码
	 * @param request
	 * @param response
	 */
	public void ValidateCode(HttpServletRequest request,HttpServletResponse response) {
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try {
            randomValidateCode.getRandcode(request, response);//输出图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	/**
	 * 
	 * @desc 加入Session域
	 * @author zhangtainrun
	 * @createDate 2018年3月28日
	 * @param String key,Object o,int MaxInactiveInterval,HttpSession session,HttpServletResponse response
	 */
	public void toSession(String key,Object o,int MaxInactiveInterval,HttpSession session,HttpServletResponse response){
		session.setAttribute(key, o);
		session.setMaxInactiveInterval(180);
		Cookie cookie = new Cookie("JSESSIONID",session.getId());         
		cookie.setMaxAge(session.getMaxInactiveInterval());  
		cookie.setPath(session.getServletContext().getContextPath());  
		response.addCookie(cookie);  
	}
	
}
