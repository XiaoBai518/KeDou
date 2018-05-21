package com.kedou.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kedou.entity.Label;
import com.kedou.entity.User;
import com.kedou.service.UserServiceImpl;

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
		if(mode.equals("jijian")) {
			return "minimalist";
		}else {
			return "optional";
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
			System.out.println("注册时 用户密码为："+pwd);
			//如果注册失败 返回 -1 否则 返回用户主键
			if(userServiceImpl.registerUser(u,request)==-1) {
				//注册失败
				model.addAttribute("info", "erro");
				return "iframe_info";
			}
			//通知前台 注册成功 待激活
			model.addAttribute("info", "succeed");
			model.addAttribute("email", u.getUserEmail());
			return "iframe_info";
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
		User u = this.userServiceImpl.findByAcount(email);
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
				//激活成功自动登陆 并且保持登陆1个小时 
				  //首次登陆设置 登录时间 和 上次登录时间 为当地时间
				    Date date = new Date();
				    u.setLoginTime(date);
			        u.setLastLoginTime(date);
				 userServiceImpl.login(u.getUserName(), u.getUserPwd(),60*60, session, response,request);
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
		User u = userServiceImpl.findByAcount(acount);
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
		int b;
		System.out.println("登陆时 用户密码为："+pwd);
		if(isAuto.equals("true")) {
			//自动登录保持7天
			b = this.userServiceImpl.login(username,pwd,7*24*60*60,session,response,request);
		}else{
			b = this.userServiceImpl.login(username,pwd,-1,session,response,request);
		}
		if(b==-2) {
			//数据库错误
			model.addAttribute("error", "DBErro");
			return "登陆界面";
		}else if(b==-1){
			System.out.println("密码错误");	
			model.addAttribute("error", "PwdErro");
				return "登陆页面";
		}else if(b==0){
			System.out.println("账号不存在");
			model.addAttribute("error", "NoAcount");
			    return "登陆页面";	
		}else if(b==1) {
			System.out.println("登陆成功");
			 return "redirect:/index.jsp"; 
		}else{
			return "错误界面";
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
		this.userServiceImpl.logout(response, session);
		 return "redirect:/index.jsp"; 
	}
	/**
	 * 展示用户个人描述语句
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/showuserdis",method=RequestMethod.GET)
	public String showUserDiscription(HttpSession session){
		List<Integer> list = new ArrayList<Integer>();
		List<String> arr = new ArrayList<String>();
		arr.add("四六级必过");
		arr.add("考研那些事");
		arr.add("驾照一本通");
		arr.add("分手吧雅思");		
		list = this.userServiceImpl.showDis();
		session.setAttribute("localList", list);//位置列表
		session.setAttribute("disarr", arr);//描述内容
		return "description"; 
	}
	
	/**
	 * 保存用户个人描述语句
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/description",method=RequestMethod.POST)
	@ResponseBody
	public String saveDis(String keyword,HttpSession session) throws IOException{	
		//User u = (User)session.getAttribute("loginUser");
		//int id = u.getUserId();
		int line = this.userServiceImpl.saveDis(keyword, 1);
		System.out.println(line);
		Gson g = new Gson();
		String m = "";
		if(line == 0){
			String s = "no";			
			m = g.toJson(s);
		}else{
			String s = "yes";
			m = g.toJson(s);
		}		
		return m;
	}
	
	/**
	 * 展示用户个人标签
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/showuserlabel",method=RequestMethod.GET)
	public String showUserLabel(HttpSession session){
		System.out.println("in");
		List<Label> label = new ArrayList<Label>();
		label = this.userServiceImpl.showUserLabel();
		session.setAttribute("labelList", label);
		return "label";
	}
	
	/**
	 * 保存用户个人标签
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/label",method=RequestMethod.POST)
	@ResponseBody
	public String saveLabel(String label,HttpSession session) throws IOException{
		//User u = (User)session.getAttribute("loginUser");
		//int id = u.getUserId();		
		int line = this.userServiceImpl.saveLabel(label,1);
		Gson g = new Gson();
		String m = "";
		if(line == 0){
			String s = "no";			
			m = g.toJson(s);
		}else{
			String s = "yes";
			m = g.toJson(s);
		}		
		return m;	
		
	}
}
