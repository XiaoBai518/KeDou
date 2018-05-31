package com.kedou.controller.user;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import com.kedou.entity.Label;
import com.kedou.entity.User;
import com.kedou.service.user.UserServiceImpl;
import com.kedou.shiro.UsernamePasswordByUserTypeToken;
import com.kedou.util.SessionUtil;
import com.kedou.util.Constants;
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
			System.out.println("用户注册的密码："+pwd);
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
					model.addAttribute("info", "registeSucceed");
					model.addAttribute("email", u.getUserEmail());
					return "iframe_info";
			} catch (Exception e) {
				e.printStackTrace();
				//注册失败
				model.addAttribute("info", "registeErro");
				return "iframe_info";
			}
			
		}
		else {
			//参数不正确返回注册页面
			model.addAttribute("info", "registeErro");
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
			e.printStackTrace();
			return "数据库错误界面";
		}
		//重新发送邮件
		try {
			this.userServiceImpl.resendEmail(email, u);
			return "发送成功";
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return "发送失败";
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
			try {
				this.userServiceImpl.changeState(Constants.BUSINESS_STATE_ACTIVE, u.getUserId());
						session.setAttribute("loginUser", u);
					//注册成功 跳转 选择标签页
					return "redirect:/user/showuserdis";
			 } catch (Exception e) {
				//更改状态失败
				e.printStackTrace();
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
	public String toiframe(@RequestParam(value="option",required=false)String option,@RequestParam(value="error",required=false)String error,
							Model model) {
		if(error!=null&&!"".equals(error)) {
			model.addAttribute("error","?error="+error);
		}
		if(option!=null&&!"".equals(option)) {
			if("regist".equals(option)) {
				model.addAttribute("url", "/user/toregist");
				return "userLogin";
			}
		}
		model.addAttribute("url", "/user/tologin");
		return "userLogin";
	}
	/**
	 * 前往登录界面
	 * @return
	 */

	@RequestMapping(value="/tologin",method=RequestMethod.GET)
	public String tologin(@RequestParam(value="error",required=false)String error,Model model) {
		if(error!=null) {
			model.addAttribute("error", error);
		}
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
	 * @throws Exception 
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@RequestParam("acount")String username,@RequestParam("userpwd")String pwd,@RequestParam("autologin") String isAuto,
			Model model,HttpSession session,HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		System.out.println("用户输入的密码："+pwd);
		UsernamePasswordByUserTypeToken token = new UsernamePasswordByUserTypeToken(username, pwd,"1");
		token.setRememberMe(true);
		
		Subject currentUser = SecurityUtils.getSubject();
	
		try {
			currentUser.login(token);
			User u = (User)currentUser.getSession().getAttribute("loginUser");
			//设置用户上次登陆 Ip
			 u.setLastLoginIp(u.getUserIp());
			 //设置用户登录Ip
			 u.setUserIp(IpAddress.getIpAddress(request));
			//更新登录信息（登录时间,登录次数）
				this.userServiceImpl.login(u);
			//保存到session
			SecurityUtils.getSubject().getSession().setAttribute("loginUser", u);
			System.out.println("登陆成功");
			return "index";
		} catch ( UnknownAccountException uae ) { 
			//账户不存在
			System.out.println("账号不存在");
			return "redirect:/user/toiframe?error=NoAcountErro";
		} catch ( IncorrectCredentialsException ice ) {
			//密码错误
			 System.out.println("密码错误");	
			return "redirect:/user/toiframe?error=PwdErro";

		} catch ( LockedAccountException lae ) {
			//已被锁定
			 System.out.println("账号被锁定");	
			return "redirect:/user/toiframe?error=stateLockErro";

		} catch ( ExcessiveAttemptsException eae ) { 
			System.out.println("d");
			return "redirect:/user/toiframe";

		} catch ( AuthenticationException ae ) {
			System.out.println("e");
			return "redirect:/user/toiframe";
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
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.logout();
		 return "index"; 
	}
	/**
	 * 跳转到个人中心
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/person",method=RequestMethod.GET)
	public String text(@RequestParam(value="address",required=false)Integer p,Model model) {
		if(p==null) {
			p = 0;
		}
		String [] address = {"user/personmessage","userhistory/personhistory","torder/persontorder","collection/personcollection"};
		model.addAttribute("address", address[p]);
		model.addAttribute("p", ++p);
		return "personal";
	
	}
	/**
	 * 切换个人中心个人介绍页面
	 * 
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/personmessage",method=RequestMethod.GET)
	public String changemessageadress(Model model,HttpSession session,HttpServletResponse response,HttpServletRequest request) {
		User u=(User) session.getAttribute("loginUser");		
			
			model.addAttribute("user", u);
			return "person_message";
		
	}

	/**
	 * 更改个人信息
	 * @param 陈
	 * @param gender
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/updateUserMessage",method=RequestMethod.POST)
	public String changgemessage(@RequestParam("username") String username,@RequestParam("gender") String gender,@RequestParam("userDiscription") String userDiscription,HttpSession session){
		User u=(User) session.getAttribute("loginUser");
		try {
		 u = userServiceImpl.updateUserMessage(username, gender,userDiscription,u.getUserId());
		session.setAttribute("loginUser", u);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/user/person";
	}
	/**
	 * 头像上传
	 * @param request
	 * @param description
	 * @param file
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value="/imgUpload",method=RequestMethod.POST)
    public String uploadUserIcon(HttpServletRequest request,@RequestParam("personImgFile") MultipartFile file,Model model,HttpSession session)  {
    	//如果文件不为空，写入上传路径
    	if(!file.isEmpty()) {
    		//开始写入文件
    		String uploadResult = this.userServiceImpl.uploadUserIcon(file);
    		if(!uploadResult.equals("-1")) {
    			//写入文件成功
    			//更新用户持久层
    	           Object object = session.getAttribute("loginUser");
    	           User u;
    	           if (object instanceof User) {
    	   			 u = (User)object;
    	   			 u.setUserIcon(uploadResult);
    	   			 try {
    					this.userServiceImpl.updateUserIcon(u);
    					//上传成功
    					//刷新session中loginUser信息
    					session.setAttribute("loginUser", u);
    					return "redirect:/user/person";
    	   			 } catch (Exception e) {
    					// TODO 自动生成的 catch 块
    					e.printStackTrace();
    					model.addAttribute("error", "DBError");
    					return "redirect:/user/person";
    	   			 }	
    		      }else {
    		    	  //loginUser 不是 User 的实例
    		    	  model.addAttribute("error", "DBError");
    		    	  return "redirect:/user/person";
    		      }
     		}else {
     			//写入文件不成功
     			 model.addAttribute("error", "upLoadFileErro");
     			return "redirect:/user/person";
     		}
           
   	   }else {
   		 //参数文件为空
   		 model.addAttribute("error", "noFileErro");
   		return "redirect:/user/person";
   	   }
		
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
		User u = (User)session.getAttribute("loginUser");
		int id = u.getUserId();
		int line = this.userServiceImpl.saveDis(keyword, id);
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
		List<Label> label = new ArrayList<Label>();
		try {
			label = this.userServiceImpl.showUserLabel();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
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
		User u = (User)session.getAttribute("loginUser");
		int id = u.getUserId();
		int line = this.userServiceImpl.saveLabel(label,id);
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
