//package com.kedou.util;
//
//import java.io.IOException;
//import java.util.Date;
//
//import javax.annotation.Resource;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.kedou.entity.User;
//import com.kedou.service.UserServiceImpl;
//
//
//public class LoginFilter implements Filter{
//	
//	  @Resource    
//	    private UserServiceImpl userServiceImpl;
//
//	@Override
//	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
//			throws IOException, ServletException {
//		HttpServletRequest request=(HttpServletRequest)arg0;     
//        HttpServletResponse response  =(HttpServletResponse) arg1;          
//		  User loginUser = (User) request.getSession().getAttribute("loginUser");  
//	        
//	        if(loginUser == null){  
//	        	System.out.println("过滤器自动登陆开始");
//	        	System.out.println(request.getRequestURI());
//	            String loginCookieUserName = "";  
//	            String loginCookiePassword = "";  
//	          
//	            Cookie[] cookies = request.getCookies();  
//	            if(cookies!=null&&cookies.length>0){    
//	                for(Cookie cookie : cookies){    
//	                    //if("/".equals(cookie.getPath())){ //getPath为null  
//	                        if("userAcount".equals(cookie.getName())){  
//	                        	System.out.println(cookie.getValue());
//	                            loginCookieUserName = cookie.getValue();  
//	                        }else if("userPwd".equals(cookie.getName())){  
//	                        	System.out.println(cookie.getValue());
//	                            loginCookiePassword = cookie.getValue();  
//	                        }  
//	                    //}  
//	                }    
//	           
//	                if(!"".equals(loginCookieUserName) && !"".equals(loginCookiePassword)){  
//	                	
//	                    User user;
//						try {
//							user = userServiceImpl.findByAcount(loginCookieUserName);
//						} catch (Exception e1) {
//							// TODO 自动生成的 catch 块
//							e1.printStackTrace();
//							user = null;
//						}
//	     
//	        			if(MD5.Md5(user.getUserPwd()).equals(loginCookiePassword)){
//	        				System.out.println("进行自动登陆");
//	        				//获取当前用户IP
//						     String IP = IpAddress.getIpAddress(request);
//						    //设置用户上次登陆IP
//						     user.setLastLoginIp(user.getUserIp());
//						    //设置用户IP
//						     user.setUserIp(IP);
//	        				//自动登陆  登录次数+1  设置上次登录时间
//	        				 user.setLoginCount(user.getLoginCount()+1);
//	        				//设置用户上次登录时间
//						     user.setLastLoginTime(user.getLoginTime());
//						    //设置用户登录时间
//						     user.setLoginTime(new Date());
//
//
//						    //更新用户登录信息
//						    try {
//								this.userServiceImpl.updateLoginInfo(user);
//							} catch (Exception e) {
//								// TODO 自动生成的 catch 块
//								e.printStackTrace();
//							}
//						     //将用户加入session域
//	        				 request.getSession().setAttribute("loginUser", user);  
//	                   }  
//	                 System.out.println("过滤器自动登陆结束");
//	                     
//	                }  
//	            }   
//	        } else {
//	        	 System.out.println(loginUser.getUserEmail());
//	        } 
//	        arg2.doFilter(request, response);
//		
//	}
//
//	public UserServiceImpl getUserServiceImpl() {
//		return userServiceImpl;
//	}
//
//	public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
//		this.userServiceImpl = userServiceImpl;
//	}
//	
//	
//}
