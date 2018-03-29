package com.kedou.service;


import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.kedou.dao.user.UserDaoImpl;
import com.kedou.entity.User;
import com.kedou.util.SendEmailThread;

@Service
public class UserServiceImpl {
	@Resource
	private UserDaoImpl userDaoImpl;
	/**
	 * 
	 * @desc 注册用户
	 * @author yuanyuan
	 * @createDate 2018年3月28日
	 * @return 用户主键
	 * @throws Exception
	 */
	public int saveUser(User u){
		try {
			this.userDaoImpl.save(u);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return u.getUserId();
	}
	/**
	 * 
	 * @desc 加入Session域
	 * @author zhangtainrun
	 * @createDate 2018年3月28日
	 * @param Object o,HttpServletRequest request,HttpServletResponse response,String key
	 */
	public void toSession(Object o,HttpSession session,HttpServletResponse response,String key){
		session.setAttribute(key, o);
		session.setMaxInactiveInterval(180);
		Cookie cookie = new Cookie("JSESSIONID",session.getId());         
		cookie.setMaxAge(session.getMaxInactiveInterval());  
		cookie.setPath(session.getServletContext().getContextPath());  
		response.addCookie(cookie);  
	}
	/**
	 * 
	 * @desc 发送邮件
	 * @author yuanyuan
	 * @createDate 2018年3月28日
	 * @return 用户激活标识码
	 * 
	 */
	public String sendEmail(String emailAddress){  
		//生成用户code
        String code=UUID.randomUUID().toString().replace("-", "");
		new SendEmailThread(emailAddress,code);  //向用户注册的邮箱发送验证码
		return code;
    }  
	/**
	 * 
	 * @desc 通过邮箱或电话查找用户
	 * @author zhangtianrun
	 * @createDate 2018年3月28日
	 * @return -1 或 0 或 1
	 *   如果邮箱或手机号已经注册则  返回0
	 *   如果邮箱或手机号可用否则     返回1
	 *   如果数据库查询错误               返回-1
	 */
	public int findByEmail(String username)  {
		User u;
		try {
			u = this.userDaoImpl.findByUsername(username);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return -1;//数据库错误
		}
			if(u!=null) {
				return 0;//已经被注册
			}else {
				return 1;//可以注册
			}
	}
	/**
	 * 
	 * @desc 登陆
	 * @author kangziyun
	 * @createDate 2018年3月29日
	 * @return -1 0 1
	 *  如果密码错误     返回 -1  
	 *  如果账号不存在  返回0
	 *  如果正确            返回 1
	 *  如果查询错误     返回-2
	 */
	public int login(String username,String pwd,HttpSession session,HttpServletResponse response) {
		User us;
		try {
			us = this.userDaoImpl.findByUsername(username);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return -2;//数据库查询错误报错
		}
		if(us!=null){
			if(us.getUserPwd().equals(pwd)){
				//加入Session域
				this.toSession(us, session, response, "user");
				return 1;//可以登陆
			}else{
				return -1;//密码错误
			}
		}else{
			return 0;//账号不存在
		}
	}
	
}
