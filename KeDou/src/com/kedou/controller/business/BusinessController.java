package com.kedou.controller.business;

import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.kedou.entity.Business;
import com.kedou.entity.Course;
import com.kedou.entity.User;
import com.kedou.service.business.BusinessServiceImpl;
import com.kedou.shiro.UsernamePasswordByUserTypeToken;
import com.kedou.util.IpAddress;
import com.kedou.util.UpLoadErro;
import com.kedou.util.UpLoadUtil;

@Controller
@RequestMapping("/business")
public class BusinessController {
	@Resource
	private BusinessServiceImpl businessServiceImpl;

	
	/**
	 * 前往机构入驻(注册)页面
	 * @author 张天润
	 * @return
	 */
	@RequestMapping(value="/toregiste",method=RequestMethod.GET)
	public String toRegiste() {
		return "businessRegister";
	}

	/**
	 * 机构注册平台账号
	 * @author 张天润
	 * @param busAccount
	 * @param busPwd
	 * @param randomCode
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/registe",method=RequestMethod.POST)
	public String registe(Business bus,Model model,HttpServletRequest request){
		 //将商家注册信息插入数据库
			//获取商家真实IP
				String IP = IpAddress.getIpAddress(request);
		    //设置商家IP
		        bus.setBusIp(IP);
		try{
			businessServiceImpl.registe(bus);
			model.addAttribute("info", "success");
			 return "businessRegister";
		}catch(Exception e){
			e.printStackTrace();
			model.addAttribute("info", "bad");
				
			return "businessRegister";
		}
	}
	/**
	 * 检测账号是否存在
	 * @author 张天润
	 * @param username
	 * @return 存在返回 -1  不存在 返回 1
	 */
	@RequestMapping(value="/isexist", method=RequestMethod.GET)
	@ResponseBody
	public String isexist(@RequestParam("acount")String acount) {
		Business bus;
		try {
			bus = businessServiceImpl.findByAcount(acount);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return "数据库错误界面";
		}
		if(bus!=null) {
			return "-1";
		}
		return "1";
	}
	/**
	 * 前往机构登录页面
	 * @author 张天润
	 * @return
	 */
	@RequestMapping(value="/tologin",method=RequestMethod.GET)
	public String toLogin() {
		return "businessLogin";
	}
	
	/**
	 * 登陆
	 * @author 张天润
	 * @param bus
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(Business bus,
						HttpSession session,HttpServletRequest request,HttpServletResponse response,Model model) throws Exception{
		UsernamePasswordByUserTypeToken token = new UsernamePasswordByUserTypeToken(bus.getBusAccount(), bus.getBusPwd(),"3");
		
		Subject currentUser = SecurityUtils.getSubject();
	
		try {
			currentUser.login(token);
			bus = (Business)currentUser.getSession().getAttribute("loginBusiness");
			//设置用户上次登陆 Ip
			bus.setLastLoginIp(bus.getBusIp());
			 //设置用户登录Ip
			bus.setBusIp(IpAddress.getIpAddress(request));
			//更新登录信息（登录时间,登录次数）
				this.businessServiceImpl.login(bus);
			//保存到session
			SecurityUtils.getSubject().getSession().setAttribute("loginBusiness", bus);
			System.out.println("登陆成功");
			return "busadmin";
		} catch ( UnknownAccountException uae ) { 
			//账户不存在
			System.out.println("账号不存在");
			model.addAttribute("error", "NoAcountErro");
		    return "businessLogin";	
		} catch ( IncorrectCredentialsException ice ) {
			//密码错误
			model.addAttribute("error", "PwdErro");
			return "businessLogin";

		} catch ( LockedAccountException lae ) {
			//已被锁定
			 System.out.println("账号被锁定");	
			 model.addAttribute("error", "stateLockErro");
				return "businessLogin";
		} catch ( ExcessiveAttemptsException eae ) { 
			System.out.println("d");
			return "businessLogin";

		} catch ( AuthenticationException ae ) {
			System.out.println("e");
			return "businessLogin";
		}
	}
	
	/**
	 * 前往商家管理中心（商家个人中心）
	 * @return
	 */
	@RequestMapping(value="/tobusadmin",method=RequestMethod.GET)
	public String toBusAdmin() {
		
		return "busadmin";
	}
	/**
	 * 前往添加课程页
	 * @return
	 */
	@RequestMapping(value="/toaddcourse",method=RequestMethod.GET)
	public String toAddCourse() {
		return "bus_addcourse";
	}

	/**
	 * 审核机构
	 * @author 原源
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/applyPass",method = RequestMethod.GET)
	private String applyPass(@RequestParam("id") int id) {//审核通过
		try {
			this.businessServiceImpl.applyPass(id);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "审核成功页面";
	}
	/**
	 * 查看机构详情
	 * @author 原源
	 * @param id
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/busDetial",method = RequestMethod.GET)
	private String busDetial(@RequestParam("id") int id,HttpSession session){//查看机构详情
		Business b=null;
		try {
			b = this.businessServiceImpl.findById(id);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if(b!=null) {
			return "提示未查到该机构";
		}
		session.setAttribute("bus", b);
		return "机构详情页";
	}
	/**
	 * 修改机构信息 第一步查询机构信息
	 * @author 原源
	 * @param id
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/busInfoModify",method = RequestMethod.GET)
	private String busModifyDetial(@RequestParam("id") int id,HttpSession session){//查看机构详情
		Business b=null;
		try {
			b = this.businessServiceImpl.findById(id);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		session.setAttribute("bus", b);
		return "机构信息修改页面";
	}
	/**
	 * 修改机构信息 第二部 修改机构信息
	 * @author 原源
	 * @param bus
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/busInfoModify",method = RequestMethod.POST)
	private String busInfoModify(Business bus) {//修改机构信息
		try {
			this.businessServiceImpl.updateBus(bus);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "提示修改成功";
	}
	/**
	 * 锁定机构
	 * @author 原源
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/busLocking",method = RequestMethod.GET)
	private String orgLock(@RequestParam("id") int id) {//锁定机构
		try {
			this.businessServiceImpl.busLock(id);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "锁定成功页面";
	}
	/**
	 * 查看机构列表
	 * @author 原源
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/busList",method = RequestMethod.GET)
	private String orgList(HttpSession session) {//查看机构列表  
		List<Business> bl = new ArrayList<Business>();
		try {
			bl = this.businessServiceImpl.findAll();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		session.setAttribute("bl", bl);
		return "机构列表";
	}
	

}
