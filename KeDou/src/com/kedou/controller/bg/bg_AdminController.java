package com.kedou.controller.bg;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kedou.entity.Admin;
import com.kedou.entity.User;
import com.kedou.service.bg.AdminServiceImpl;
import com.kedou.shiro.UsernamePasswordByUserTypeToken;
import com.kedou.util.IpAddress;

@Controller
@RequestMapping("/bg_admin")
public class bg_AdminController {
	@Resource
	private AdminServiceImpl adminServiceImp;
	
	/**
	 * 锁定用户
	 * @param str
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/bgLockUser",method=RequestMethod.POST)
	@ResponseBody
	public String lockUser(@RequestParam("str")String str,HttpServletRequest request) {
		
		if(str!=null) {
		Gson g = new Gson();
		
		 List<User> ul = g.fromJson(str, new TypeToken<List<User>>() {}.getType());//对于不是类的情况，用这个参数给出)
		 int temp=0;
		 int[] array=new int[ul.size()];
		for(User u:ul) {
			array[temp]=u.getUserId();
			temp++;
		}
		adminServiceImp.lockUser(array);
		}else {
			
		}
		
		return null;
		
	}
	/**
	 * 修改用户 基本实现具体范围不确定
	 * @param userName
	 * @param userId
	 * @param userEamil
	 * @param userPwd
	 * @param userTel
	 * @param userIdNum
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/bgEditUser",method=RequestMethod.POST)
	public String editUser(@RequestParam("userName")String userName,
							@RequestParam("userId")String userId,
							@RequestParam("userEmail")String userEamil,
							@RequestParam("userPwd")String userPwd,
							@RequestParam("userTel")String userTel,
							@RequestParam("userIdNum")String userIdNum,
							Model model,HttpSession session,HttpServletRequest request) {
		User us=adminServiceImp.findUser(userName);	
		us.setUserName(userName);
		us.setUserId(Integer.parseInt(userId));
		us.setUserEmail(userEamil);
		us.setUserPwd(userPwd);
		us.setUserTel(userTel);
		us.setUserIdNum(userIdNum);
		adminServiceImp.editUser(us);
		return "bg_index";
		
	}
	
	
	/**
	 * ajax在编辑用户信息页面动态查询
	 * @param account
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/bgSearchUser",method=RequestMethod.GET,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String searchUser(@RequestParam("account")String account,Model model,HttpSession session,HttpServletRequest request) {
		User us=adminServiceImp.findUser(account);
		if(us!=null) {
			System.out.println(us.getUserName());
			Gson g = new Gson();
			
			return g.toJson(us);
		}else {
			System.out.println("false");
			return "";
		}
		
		
	}
	/**
	 * 添加管理员
	 * @author 宋亚楼
	 * @return "bg_index"
	 * 
	 */
	@RequestMapping(value="/bgaddAdmin",method=RequestMethod.POST)
	public String addAdmin(@RequestParam("adminAccount")String bg_account,
							@RequestParam("adminPwd")String bg_pwd,
							@RequestParam(value="adminImg",required=false)String bg_img,
							Model model,HttpSession session,HttpServletRequest request) {
		//数据库中寻找是否存在管理员
		Admin ad=adminServiceImp.findAdminAccount(bg_account);
		if(ad==null) {
			ad = new Admin();
			ad.setAdminAccount(bg_account);
			ad.setAdminPwd(bg_pwd);
			try {
				//为管理员做注册
				ad=adminServiceImp.registerAdmin(ad);
				System.out.println("账号注册成功");
				return "bg_index";
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("账号异常");
				return "bg_addAdmin";
			}
		}else {
			System.out.println("账号已经存在");
			return "bg_addAdmin";
		}
	
	}
	
	/**
	 *登陆
	 * @author 宋亚楼
	 *
	 * @return index
	 * @throws Exception 
	 */
	@RequestMapping(value="/bglogin",method=RequestMethod.POST)
	public String BgLogin(@RequestParam("bg_account")String bg_account,
							@RequestParam("bg_pwd")String bg_pwd,
							Model model,HttpSession session,HttpServletResponse response,HttpServletRequest request) throws Exception {
		
		UsernamePasswordByUserTypeToken token = new UsernamePasswordByUserTypeToken(bg_account, bg_pwd,"2");
		
		
		Subject currentUser = SecurityUtils.getSubject();
	
		try {
			currentUser.login(token);
			Admin ad = (Admin)currentUser.getSession().getAttribute("loginAdmin");
			//设置用户上次登陆 Ip
			 ad.setLastLoginIp(ad.getLoginIp());
			 //设置用户登录Ip
			 ad.setLoginIp(IpAddress.getIpAddress(request));
			//更新登录信息（登录时间,登录次数）
				this.adminServiceImp.adminLogin(ad);
				//保存到session
				SecurityUtils.getSubject().getSession().setAttribute("loginAdmin", ad);
			System.out.println("登陆成功");
			
			return "bg_index";
		} catch ( UnknownAccountException uae ) { 
			System.out.println("a");
			return "bg_login";

		} catch ( IncorrectCredentialsException ice ) {
			System.out.println("b");
			return "bg_login";

		} catch ( LockedAccountException lae ) {
			System.out.println("c");
			return "bg_login";

		} catch ( ExcessiveAttemptsException eae ) { 
			System.out.println("d");
			return "bg_login";

		} catch ( AuthenticationException ae ) {
			System.out.println("e");
			return "bg_login";
		}
	}
}
