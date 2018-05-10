package com.kedou.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kedou.entity.User;
import com.kedou.service.UserServiceImpl;
import com.kedou.util.AutoLogin;
import com.kedou.util.IpAddress;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserServiceImpl userServiceImpl;

	/**
	 * 切换搜索模式
	 * @param mode
	 * @return
	 */
	@RequestMapping(value="switchMode",method=RequestMethod.GET)
	public String switchingMode(@RequestParam("mode") String mode) {
		if(mode.equals("minimalist")) {
			return "iframe_minimalist";
		}else {
			return "iframe_optional";
		}
	}
	/**
	 * 前往注册界面
	 * @return
	 */
	@RequestMapping(value="toregist",method=RequestMethod.GET)
	public String toregist() {
		return "iframe_registe";
	}
	/**
	 * 用户注册
	 * @param info 传来的登陆账号是Email 还是手机号
	 * @param acount
	 * @param pwd
	 * @param session
	 * @return
	 * 当用户未收到发送的邮件时，仍调用此方法
	 */
	@RequestMapping(value="/registe",method=RequestMethod.POST)
	public String  regist(@RequestParam("acounttype") String acounttype,
						  @RequestParam("acount")String acount,
			              @RequestParam("userpwd")String pwd,
			              HttpSession session,HttpServletResponse response,Model model,HttpServletRequest request){
		if(acounttype.equals("tel")){//用户选择的手机号注册
			/*
			 * 
			 * 
			 * 
			 * 手机注册
			 * 
			 * 
			 * 
			 */
			return "提示用户输入短信验证码";
		}else if (acounttype.equals("email")) {//用户选择的邮箱注册
			//创建用户对象
			User u = new User(acount,pwd);
			 //获取用户真实IP
			 String IP = IpAddress.getIpAddress(request);
			    //设置用户IP
			     u.setUserIp(IP);
			//如果注册失败 返回 -1 否则 返回用户主键
			try {
				userServiceImpl.registerUser(u);
					//通知前台 注册成功 待激活
					model.addAttribute("info", "succeed");
					model.addAttribute("email", u.getUserEmail());
					return "iframe_info";
			} catch (Exception e) {
				e.printStackTrace();
				//注册失败
				model.addAttribute("info", "erro");
				return "iframe_info";
			}
			
		}
		else {
			//参数不正确返回注册页面
			model.addAttribute("info", "erro");
			return "iframe_info";
			
		}
	}
	/**
	 * 重新发送邮件
	 * @param useremail
	 * @return 发送失败返回 -1  发送成功 返回userid
	 */
	@RequestMapping(value="/resend", method=RequestMethod.GET)
	@ResponseBody
	public String resend(@RequestParam("useremail") String email) {
		//按邮箱地址查找用户
		User u;
		try {
			u = this.userServiceImpl.findByAcount(email);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return "数据库错误界面";
		}
		//重新发送邮件
		if(this.userServiceImpl.resendEmail(email, u)!=-1) {
			//发送成功
			return  u.getUserId()+"";
		}else {
			return "-1";
		}
	}
	/**
	 * 邮箱注册账号激活
	 * @param userVerifyNum
	 * @param session
	 * @return
	 */
	@RequestMapping(value="verify",method=RequestMethod.GET)
	public String verify(@RequestParam("verifyNum") String userVerifyNum,
			HttpSession session,HttpServletResponse response,Model model,HttpServletRequest request){
		//通过参数中的 激活验证码 在数据库查询用户
		User u = userServiceImpl.findByVerifyNum(userVerifyNum);

		if(u!=null&&u.getVerifyNum().equals(userVerifyNum)){
			//用户点击的链接所带的激活验证码与系统随机生成的激活验证码一致
			  //激活账户 将账户状态变为 正常状态 （1） 激活成功返回用户ID  否则 返回 -1
			if(this.userServiceImpl.changeState(1, u.getUserId())!=-1) {
				  //首次登陆设置 登录时间 和 上次登录时间 为当地时间
				    Date date = new Date();
				    u.setLoginTime(date);
			        u.setLastLoginTime(date);
			      //设置用户IP
			       String IP = IpAddress.getIpAddress(request);
			        u.setUserIp(IP);
			        u.setLastLoginIp(IP);
			        //加入session
			      session.setAttribute("loginUser", u);
				return "index";
			}else {
				//返回错误信息
				model.addAttribute("erro", "erro");
				return "注册提示页面";
			}
			
		}else {//用户点击的链接所带的验证码与系统随机生成的验证码不一致
			return "提示激活地址错误，请使用正确的激活地址";			
		}
		
	}

	/**
	 * 检测账号是否存在
	 * @param username
	 * @return 存在返回 -1  不存在 返回 1
	 */
	@RequestMapping(value="/isexist", method=RequestMethod.GET)
	@ResponseBody
	public String isexist(@RequestParam("acount")String acount) {
		User u;
		try {
			u = userServiceImpl.findByAcount(acount);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return "数据库错误界面";
		}
		if(u!=null) {
			return "-1";
		}
		return "1";
	}
	/**
	 * 前往登录注册界面
	 * @return
	 */
	@RequestMapping(value="/toiframe",method=RequestMethod.GET)
	public String toiframe(Model model) {
		model.addAttribute("page", "user/tologin");
		return "userLogin";
	}
	/**
	 * 前往登录界面
	 * @return
	 */
	@RequestMapping(value="/tologin",method=RequestMethod.GET)
	public String tologin(Model model) {
		return "iframe_login";
	}
	/**
	 * 登陆
	 * @param username
	 * @param pwd
	 * @param model
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@RequestParam("acount")String username,@RequestParam("userpwd")String pwd,@RequestParam("autologin") String isAuto,
			Model model,HttpSession session,HttpServletResponse response,HttpServletRequest request){
		
		User us;
		try {
			us = this.userServiceImpl.findByAcount(username);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return "数据库错误界面";
		}
		if(us!=null) {
			//账户存在
				//自动登录保持7天
				 us = this.userServiceImpl.login(us,pwd);
				 if(us!=null) {
					 //密码正确
					 if(us.getState()!=1) {
						 if(us.getState()==0) {
							 //未激活
							 System.out.println("未激活");	
								model.addAttribute("error", "stateActiveErro");
									return "登陆页面";
						 }else {
							//已被锁定
							 System.out.println("账号被锁定");	
								model.addAttribute("error", "stateLockErro");
									return "登陆页面";
						 }
						
					 }else {
						 //密码正确且状态可登录
						   //获取当前用户IP
					       String IP = IpAddress.getIpAddress(request);
					       	   //设置用户上次登陆IP
					       	 	us.setLastLoginIp(us.getUserIp());
					       	   //设置用户IP
					       	 	us.setUserIp(IP);
					    //用户自动登录类
						 AutoLogin<User> autologin = new AutoLogin<User>();
						 
						 if(isAuto.equals("true")) {
							 //自动登陆
							 autologin.autoLogin(us,username,us.getUserPwd(), 7*24*60*60, response, session);
						 }else {
							 //不自动登录
							 autologin.autoLogin(us,username,us.getUserPwd(), -1, response, session);
						 }
						 System.out.println("登陆成功");
						 return "index";
					 }
				 }else {
					 //密码错误
					 System.out.println("密码错误");	
						model.addAttribute("error", "PwdErro");
							return "登陆页面";
				 } 
		}else {
			//账户不存在
			System.out.println("账号不存在");
			model.addAttribute("error", "NoAcount");
			    return "登陆页面";	
		}
		
	}
	/**
	 * 注销
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(HttpSession session,HttpServletResponse response){
		 Cookie userNameCookie = new Cookie("userAcount", null);  
		    Cookie passwordCookie = new Cookie("userPwd", null);  
		    userNameCookie.setMaxAge(0);  
		    userNameCookie.setPath("/"); 
		    
		    passwordCookie.setMaxAge(0);  
		    passwordCookie.setPath("/");  
		    
		    response.addCookie(userNameCookie);  
		    response.addCookie(passwordCookie);  
		      
		    session.removeAttribute("loginUser");  
		 return "index"; 
	}

}
