package com.kedou.util;

import java.net.URLDecoder;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kedou.entity.Address;


public class AddressInterceptor implements HandlerInterceptor {

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object  handler) throws Exception {
		System.out.println("地址拦截器开始");
    	Address address =  (Address)request.getSession().getAttribute("userAddress");  
        System.out.println(request.getRequestURI());

        
        if(address == null){  
        	
            String userAddressJson = "";  
            
            Cookie[] cookies = request.getCookies();  
            if(cookies!=null&&cookies.length>0){    
                for(Cookie cookie : cookies){         
                        if("userAddressC".equals(cookie.getName())){  
                            userAddressJson = cookie.getValue();  
                        }
                  
                }    
           
                if(!"".equals(userAddressJson) ){  
                	Gson g = new Gson();
                	userAddressJson = URLDecoder.decode(userAddressJson,"utf-8");
                	address = g.fromJson(userAddressJson, Address.class);
                	  //将用户加入session域
   				 	request.getSession().setAttribute("userAddress", address);  
                }  else {
                	// 没有相应Cookie
                	
                }
            }   
        }
        System.out.println("地址拦截器结束");
        return true;  
	}
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO 自动生成的方法存根

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO 自动生成的方法存根

	}

	

}
