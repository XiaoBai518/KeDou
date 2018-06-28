package com.kedou.controller.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Log4jConfigurer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kedou.entity.Business;
import com.kedou.entity.BusinessVisitNumber;
import com.kedou.service.business.BusinessServiceImpl;
import com.kedou.service.torder.TorderServiceImpl;
import com.kedou.service.visitNumber.VisitNumberServiceImpl;
import com.kedou.shiro.UsernamePasswordByUserTypeToken;
import com.kedou.superentity.SuperChart;
import com.kedou.util.Constants;
import com.kedou.util.IpAddress;

@Controller
@RequestMapping("/business")
public class BusinessController {
	@Resource
	private BusinessServiceImpl businessServiceImpl;
	
	@Resource
	private TorderServiceImpl torderServiceImpl;
	
	@Resource
	private VisitNumberServiceImpl visitNumberServiceImpl;
	
	@Resource(name="springcacheManager")
	private EhCacheCacheManager CacheManager;

	
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
		
		//商家登录 将用户账户注销
			currentUser.logout();
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
			return "redirect:/business/tobusadmin";
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
	 * 前往商家个人店铺首页
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/tohome",method=RequestMethod.GET)
	public String toHome(@RequestParam("businessId") int id) {
		return "forward:/course/toBusinessHomes";
	}
	
	/**
	 * 前往商家管理中心（商家个人中心）
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/tobusadmin",method=RequestMethod.GET)
	public String toBusAdmin(Model model)  {
		Business bus = (Business)SecurityUtils.getSubject().getSession().getAttribute("loginBusiness");
		//查询未处理的顶单个数
		
		model.addAttribute("busid", bus.getBusId());
		try {
			int count =(int) this.torderServiceImpl.findOrderCountByBusId(bus.getBusId(),0);
			 model.addAttribute("untreatedCount", count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "busadmin";
	}
	/**
	 * 前往 店铺数据展示页面（商家个人中心）
	 */
	@RequestMapping(value="/toRealData",method=RequestMethod.GET)
	public String toRealData(Model model) {
			//目前正在登录的商家
		Subject currentUser = SecurityUtils.getSubject();
		Business bus = (Business)currentUser.getSession().getAttribute("loginBusiness");
		int busid = bus.getBusId();
		//查询今日预约成功个数
		try {
			long Todaycount = this.torderServiceImpl.findOrderCountByDate(busid, Constants.TORDER_STATE_TREATED,new Date()); 
			model.addAttribute("todayTorderCount", Todaycount);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("todayTorderCount", 0);
		} 
		//查询历史预约成功个数
		try {
			long Allcount = this.torderServiceImpl.findOrderCountByBusId(busid, Constants.TORDER_STATE_TREATED); 
			model.addAttribute("allTorderCount", Allcount);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("allTorderCount", 0);
		} 
		
		//查询今日访问量
		Cache cache = CacheManager.getCache("visitNumberCache");
		ValueWrapper value = cache.get("visitNumberMap");
		
		if(value==null) {
			model.addAttribute("visitNumber", 0);
		}else {
			Map<Integer,BusinessVisitNumber> visitNumberMap = (Map<Integer,BusinessVisitNumber>)value.get();
			if(visitNumberMap.get(busid)==null) {
				BusinessVisitNumber bvn = new BusinessVisitNumber(busid);
				visitNumberMap.put(busid,bvn);
				try {
					this.visitNumberServiceImpl.addBusinessVistNumber(bvn);
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				model.addAttribute("visitNumber",visitNumberMap.get(busid).getVisitNumber());
			}else {
				model.addAttribute("visitNumber",visitNumberMap.get(busid).getVisitNumber());
			}
		
		}
		
		//查询最近一周的订单数量
		;
		try {
			model.addAttribute("gsonSuperChart",new Gson().toJson(this.torderServiceImpl.createSuperChartBusId(busid)));
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("gsonSuperChart",new Gson().toJson(new SuperChart(new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0,0)))));
		}
		
		return "bus_realdata";
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
	 * 前往 (机构个人中心)修改机构个人信息页面
	 * @return
	 */
	@RequestMapping(value="/tobusInfo",method=RequestMethod.GET)
	public String toBusInfo(@RequestParam("busid")int busid,Model model) {
		
		Business bus = null;
		try {
			bus = this.businessServiceImpl.findById(busid);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		model.addAttribute("business", bus);
		return "bus_basicinfo";
	}
	/**
	 * 修改机构个人信息(商家个人中心)
	 * @param value
	 * @param key
	 * @return
	 */
	@RequestMapping(value="/ModifybusInfo",method=RequestMethod.POST)
	@ResponseBody
	public String modifyBusInfo(@RequestParam("value")String value,@RequestParam("key")String key,
								@RequestParam("busid")String busid) {
		this.businessServiceImpl.updateBusInfo(key, value, busid);
		
		return value;
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
