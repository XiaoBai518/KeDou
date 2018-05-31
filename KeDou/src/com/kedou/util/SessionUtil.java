package com.kedou.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

public class SessionUtil {
	
	/**
	 * 自动登录
	 * @param u
	 * @param time 单位秒
	 * @param response
	 */
	public static void autoLogin(Object entity,String account,String pwd,int time,HttpServletResponse response,HttpSession session) {
		  Cookie cookie1 = new Cookie("userAccount",account);
		  cookie1.setMaxAge(time);//设置有效期
		  cookie1.setPath("/");//可在同一应用服务器内共享方法
		  
		
		
		  Cookie cookie2 = new Cookie("userPwd",MD5.Md5(pwd));
		  cookie2.setMaxAge(time);//设置有效期
		  cookie2.setPath("/");//可在同一应用服务器内共享方法
		  
		  response.addCookie(cookie1);//发送到客户段
		  response.addCookie(cookie2);//发送到客户段
		  
		  
		  session.setAttribute("loginUser", entity);
	}
	
	/**
	 * 加入session域
	 * @param entity
	 * @param sessionName
	 * @param time
	 * @param response
	 * @param session
	 */
	public static void toSession (String sessionKey,Object sessionValue,String cookieKey,Object cookieValue,int time,HttpServletResponse response,HttpSession session) {
		
		String cookievalue = null;
		if(cookieValue instanceof java.lang.String){
	           cookievalue = (String)cookieValue;
	     }else {
	    		Gson g = new Gson();
	    		cookievalue =  g.toJson(cookieValue);
	    		try {
	    			cookievalue = URLEncoder.encode(cookievalue, "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
	  
	     }
		
		Cookie cookie = new Cookie(cookieKey,cookievalue);
		  cookie.setMaxAge(time);//设置有效期
		  cookie.setPath("/");//可在同一应用服务器内共享方法

		  response.addCookie(cookie);//发送到客户段
		  session.setAttribute(sessionKey, sessionValue);
		
	}

}
