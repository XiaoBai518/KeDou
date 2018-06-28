package com.kedou.controller.user.informationBinding;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyuncs.exceptions.ClientException;
import com.kedou.entity.User;
import com.kedou.service.user.UserServiceImpl;
import com.kedou.util.BCrypt;
import com.kedou.util.Constants;
import com.kedou.util.SendTelCode;

@Controller
@RequestMapping("/InformationBinding")
public class InformationBindingController {
	@Resource
	private UserServiceImpl userServiceImpl;
	private SendTelCode sendTelCode;
	/**
	 * 跳转到绑定信息页面
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value="binding",method=RequestMethod.GET)
	public String tochangPwd(Model model,HttpSession session){
		User u=(User) session.getAttribute("loginUser");
		model.addAttribute("user", u);
		return "person_InformationBinding";
	}
	/**
	 * ajax判断输入的旧密码输入是否正确
	 * */
	@RequestMapping(value="checkpwd",method=RequestMethod.GET)
	@ResponseBody
	public String checkPwd(Model model,HttpSession session,@RequestParam(value="pwd") String pwd){
		User u=(User) session.getAttribute("loginUser");	
		User user = (User)SecurityUtils.getSubject().getSession().getAttribute("loginUser");
		return BCrypt.checkpw(pwd+user.getSalt(), user.getUserPwd())?"true":"false";
	}
	/**
	 * 更改用户密码
	 * */
	@RequestMapping(value="changepwd",method=RequestMethod.GET)
	@ResponseBody
	public String changePwd(Model model,HttpSession session,@RequestParam(value="usernewpwd1") String newpwd){
		User u = (User)SecurityUtils.getSubject().getSession().getAttribute("loginUser");
		 
		//随机生成 salt
		String salt =UUID.randomUUID().toString().replace("-", "");
		   //进行二次加密
		String newpwd1= BCrypt.hashpw(newpwd+salt, BCrypt.gensalt());
		u.setUserPwd(newpwd1); 
		   //设置salt值
		u.setSalt(salt);
		SecurityUtils.getSubject().getSession().setAttribute("loginUser",u);		
		try {
			userServiceImpl.updateUserPwd(newpwd1,salt,u.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "1";
	}
	/**
	 *ajax验证手机号输入是否正确 
	 **/
	@RequestMapping(value="checktel",method=RequestMethod.GET)
	@ResponseBody
	public String checkTel(Model model,HttpSession session,@RequestParam(value="tel") String tel){
		User u=(User) session.getAttribute("loginUser");	
		return tel.equals(u.getUserTel())?"true":"false";
	}
	/**
	 *ajax验证邮箱账号输入是否正确 
	 **/
	@RequestMapping(value="checkmail",method=RequestMethod.GET)
	@ResponseBody
	public String checkmail(Model model,HttpSession session,@RequestParam(value="mailcount") String mailcount){
		User u=(User) session.getAttribute("loginUser");	
		System.out.println("dhkahdahkdfghd");
		return mailcount.equals(u.getUserEmail())?"true":"false";
	}	
	/**
	 *ajax给新输入手机号发送验证码 
	 **/
	@RequestMapping(value="sendtelcode",method=RequestMethod.GET)
	@ResponseBody
	public String sendTelNum(HttpSession session,@RequestParam(value="tel") String tel){
		//生成1个六位数随机数
		String str = "";//0 1 2 3 4 5
		for(int i = 0 ; i < 6;i++){
			str+=(int)Math.floor(Math.random()*10);
		}
		//将验证码添加到session中
		session.setAttribute("code", str);
		try {
			sendTelCode.sendSms(tel,str);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("验证码为  "+str);
		return str;
	} 
	/**
	 *ajax 更改用户手机号 
	 **/
	@RequestMapping(value="changetel",method=RequestMethod.GET)
	@ResponseBody
	public String changeTel(HttpSession session,@RequestParam(value="tel") String tel){
		User u=(User) session.getAttribute("loginUser");
		u.setUserTel(tel);
		try {
			userServiceImpl.updateUserTelNum(tel, (int)u.getUserId());
			session.setAttribute("loginUser", u);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "1";
	}
	/**
	 *ajax给邮箱发送验证码
	 **/
	@RequestMapping(value="sendmailcode",method=RequestMethod.GET)
	@ResponseBody
	public String sendmailcode(HttpSession session,@RequestParam(value="mailcount") String emailAddress){
		//生成1个六位数随机数
		String str = "";//0 1 2 3 4 5
		for(int i = 0 ; i < 6;i++){
			str+=(int)Math.floor(Math.random()*10);
		}
		session.setAttribute("mailcode", str);
		System.out.println("邮箱为    "+emailAddress);
		System.out.println("验证码为     "+str);
		try {
			userServiceImpl.sendEmailcode(emailAddress, str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	/**
	 *ajax绑定邮箱对数据库进行修改
	 **/
	@RequestMapping(value="bingmail",method=RequestMethod.GET)
	@ResponseBody
	public String bingmail(HttpSession session,@RequestParam(value="mailcount") String mailcount){
		User u=(User) session.getAttribute("loginUser");
		u.setUserEmail(mailcount);
		try {
			userServiceImpl.bingUsermail(mailcount, (int)u.getUserId());
			session.setAttribute("loginUser", u);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "1";
	}
	
}
