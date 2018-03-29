package com.kedou.controller;

import javax.annotation.Resource;
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

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserServiceImpl userServiceImpl;
	/*
	 * 用户注册
	 * 
	 * 当用户未收到发送的邮件时，仍调用此方法
	 */
	@RequestMapping(value="/regist",method=RequestMethod.POST)
	public String  regist(@RequestParam("info") String info,User u,HttpSession session){
		if(info.equals("tel")){//用户选择的手机号注册
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
		}else if (info.equals("email")) {//用户选择的邮箱注册
		
			String code = userServiceImpl.sendEmail(u.getUserEmail());//向用户输入的邮箱发送验证邮件
			session.setAttribute("verifyNum",code);
			session.setAttribute("emailAddress", u.getUserEmail());
			session.setAttribute("password", u.getUserPwd());
			return "提示邮件已发送，请前去激活";
		}
		else {
			//参数不正确返回注册页面
			return "注册页面";
		}
	}
	/*
	 * 
	 * 激活
	 */
	@RequestMapping(value="verify",method=RequestMethod.GET)
	public String verify(@RequestParam("verifyNum") String userVerifyNum,HttpSession session){
		
		String verifyNum = (String) session.getAttribute("verifyNum");//获取到系统随机生成的验证码
		String emailAddress = (String) session.getAttribute("emailAddress");//获取到用户的注册邮箱
		String pwd = (String) session.getAttribute("password");//获取到用户的密码
		
		if(verifyNum.equals(userVerifyNum)){//用户点击的链接所带的验证码与系统随机生成的验证码一致
			User u = new User(emailAddress,pwd);
			this.userServiceImpl.saveUser(u);//将用户的注册信息查到数据库中
			return "跳转首页";
		}else {//用户点击的链接所带的验证码与系统随机生成的验证码不一致
			return "提示激活地址错误，请使用正确的激活地址";			
		}
		
	}
	/*
	 * 验证邮箱或手机号是否存在
	 */
	@RequestMapping(value="/isexit", method=RequestMethod.GET)
	@ResponseBody
	public String isexit(@RequestParam("username")String username) {
		int a = userServiceImpl.findByEmail(username);
		return a+"";
	}
	/*
	 * 
	 * 验证码是否正确
	 */
	@RequestMapping(value="/istrue", method=RequestMethod.GET)
	@ResponseBody
	public String istrue(@RequestParam("verifyCode")String verify,HttpSession session) {
		String origincode = (String) session.getAttribute("verifyCode");
		if(origincode.equalsIgnoreCase(verify)) {
			//验证码正确 返回1
			return "1";
			
		}else {
			//验证码错误 返回-1
			return "-1";
		}
		
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@RequestParam("username")String username,@RequestParam("pwd")String pwd,Model model,HttpSession session,HttpServletResponse response){
		int b=this.userServiceImpl.login(username,pwd,session,response);
		if(b==-1){
			System.out.println("密码错误");	
			model.addAttribute("error", "pwd");
				return "登陆页面";
		}else if(b==0){
			System.out.println("账号不存在");
			model.addAttribute("error", "account");
			    return "登陆页面";	
		}else if(b==1) {
			System.out.println("登陆成功");
			    return "首页";
		}
		return "错误页面";

	}

}
