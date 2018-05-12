package com.kedou.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.kedou.entity.Business;
import com.kedou.service.BusinessServiceImpl;
import com.kedou.service.common.CommonServiceImpl;
import com.kedou.util.AutoLogin;
import com.kedou.util.IpAddress;

@Controller
@RequestMapping("/business")
public class BusinessController {
	@Resource
	private BusinessServiceImpl businessServiceImpl;
	@Resource
	private CommonServiceImpl commonServiceImpl;//公共的方法实现
	
	/**
	 * 前往机构入驻页面
	 * @return
	 */
	@RequestMapping(value="/toregiste",method=RequestMethod.GET)
	public String toRegiste() {
		return "businessRegister";
	}

	/**
	 * 机构注册平台账号
	 * @author 杨子强
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
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(Business bus,@RequestParam("autologin") String isAuto,
						HttpSession session,HttpServletRequest request,HttpServletResponse response,Model model){
		Business bu;
		try {
			//根据账户查找机构账号
			bu = this.businessServiceImpl.findByAcount(bus.getBusAccount());
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return "数据库错误界面";
		}
		if(bu!=null) {
			//账户存在
				//检查密码是否正确
				 bu = this.businessServiceImpl.login(bu,bu.getBusPwd());
				 if(bu!=null) {
					 //密码正确
					 if(bu.getState()!=1) {
						 if(bu.getState()==0) {
							 //未激活
							 System.out.println("未激活");	
								model.addAttribute("error", "stateActiveErro");
									return "登陆页面";
						 }else{
							//已被锁定
							 System.out.println("账号被锁定");	
								model.addAttribute("error", "stateLockErro");
									return "登陆页面";
						 }
						
					 }else {
						 //密码正确且状态可登录
						   //获取当前机构IP
					       String IP = IpAddress.getIpAddress(request);
					       	   //设置机构上次登陆IP
					       	 	bu.setLastLoginIp(bu.getBusIp());
					       	   //设置机构IP
					       	 	bu.setBusIp(IP);
					    //用户自动登录类
						 AutoLogin<Business> autologin = new AutoLogin<Business>();
						 
						 if(isAuto.equals("true")) {
							 //自动登陆
							 autologin.autoLogin(bu,bu.getBusAccount(),bu.getBusPwd(), 7*24*60*60, response, session);
						 }else {
							 //不自动登录
							 autologin.autoLogin(bu,bu.getBusAccount(),bu.getBusPwd(), -1, response, session);
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
	 * 审核机构
	 * @author 原源
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/applyPass",method = RequestMethod.GET)
	private String applyPass(@RequestParam("id") int id) {//审核通过
		this.businessServiceImpl.applyPass(id);
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
		Business b = this.businessServiceImpl.findById(id);
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
		Business b = this.businessServiceImpl.findById(id);
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
		this.businessServiceImpl.updateBus(bus);
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
		this.businessServiceImpl.busLock(id);
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
		bl = this.businessServiceImpl.findAll();
		session.setAttribute("bl", bl);
		return "机构列表";
	}

}
