package com.kedou.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kedou.entity.User;

public class AutoLogin<T> {
	
	/**
	 * 自动登录
	 * @param u
	 * @param time 单位秒
	 * @param response
	 */
	public  void autoLogin(T entity,String account,String pwd,int time,HttpServletResponse response,HttpSession session) {
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

}
