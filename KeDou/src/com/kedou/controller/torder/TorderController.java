package com.kedou.controller.torder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.kedou.entity.Business;
import com.kedou.entity.Course;
import com.kedou.entity.Torder;
import com.kedou.entity.User;
import com.kedou.service.course.CourseServiceImpl;
import com.kedou.service.torder.TorderServiceImpl;
import com.kedou.service.user.UserServiceImpl;
import com.kedou.superentity.SuperTorder;
import com.kedou.util.Checkbox;
import com.kedou.util.Constants;

@Controller
@RequestMapping("/torder")
public class TorderController {
	@Resource
	private TorderServiceImpl torderServiceImpl;
	@Resource
	private CourseServiceImpl courseServiceImpl;
	@Resource
	private UserServiceImpl userServiceImpl;
	
	
	/**
	 * 前往下订单页面
	 * @return
	 */
	@RequestMapping(value="toStartOrder",method=RequestMethod.GET)
	public String toStartOrder(@RequestParam("courseId")int courseid,Model model) {
		model.addAttribute("courseId", courseid);
		return "startOrder";
	}
	/**
	 * 下订单
	 * @return
	 */
	@RequestMapping(value="startOrder",method=RequestMethod.POST)
	public String StartOrder(Torder order) {
		try {
			Course course =  this.courseServiceImpl.findByCourseId(order.getCourseId());
			//设置商家Id
			order.setBusId(course.getBusId());
			//设置价格
			order.setPrice(course.getCoursePrice()*order.getReserveNum());
			//折折订单创建时间
			order.setOrderCreateTime(new Date());
			//存入数据库
			this.torderServiceImpl.SaveTorder(order);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "waitOrder";
	}
	/**
	 * 商家 处理 订单
	 */
	@RequestMapping(value="treatedTorder",method=RequestMethod.GET)
	public String TreatedTorder(@RequestParam("orderId") int orderId) {
			try {
				this.torderServiceImpl.updateState(Constants.TORDER_STATE_TREATED,new Date(), orderId);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		return "redirect:/business/tobusadmin";
	}
	/**
	 * 商家管理中心（商家个人中心）查看 预约页面 (分页)
	 * 
	 * @param p 判断是未处理预约还是 已处理预约
	 * @param pagenum
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/tobustorder",method=RequestMethod.GET)
	public String toBusTorder(@RequestParam("page")int pagenum,@RequestParam("p")String p,Model model,HttpSession session,HttpServletResponse response,HttpServletRequest request) {
		Business bus = (Business)SecurityUtils.getSubject().getSession().getAttribute("loginBusiness");
		List<Torder> list = null;
		List<SuperTorder> superTorderList = null;
		int count = 0;
		String url="";
		try {
			if("untreated".equals(p)) {
				//分页查询 未处理的预约订单
				list = this.torderServiceImpl.findOrderByBusIdPage(pagenum,Constants.BUSINESSADMIN_DEFAULT_PAGESIZE,bus.getBusId(),0);
				//获得未处理的预约订单个数
				count =(int) this.torderServiceImpl.findOrderCountByBusId(bus.getBusId(),0);
				url = "bus_untreatedTorder";
			}else if("treated".equals(p)) {
				//分页查询 已处理的预约订单
				list = this.torderServiceImpl.findOrderByBusIdPage(pagenum,Constants.BUSINESSADMIN_DEFAULT_PAGESIZE,bus.getBusId(),1);
				//获得已处理的预约订单个数
				count =(int) this.torderServiceImpl.findOrderCountByBusId(bus.getBusId(),1);
				 url = "bus_treatedTorder";
			}	
			//有订单
			if(list!=null&&list.size()!=0) {
				//将 用户 课程相关信息 存入SuperTorder 中
				//创建userIdList集合
				List<Integer> useridList = new ArrayList<Integer>();
				List<Integer> courseidList = new ArrayList<Integer>();
				for(Torder t:list) {
					useridList.add(t.getUserId());
					courseidList.add(t.getCourseId());
				}
				Map<Integer,User> userMap= this.userServiceImpl.findUserListByUserIdlist(useridList);
				Map<Integer,Course> courseMap = this.courseServiceImpl.findCourseMapByIdList(courseidList);
			  superTorderList = this.torderServiceImpl.createSuperTorder(courseMap, userMap, list);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return "数据库错误界面";
		}
		
		model.addAttribute("pagecount",count);
		model.addAttribute("page", "1");
		model.addAttribute("torderlist", superTorderList);
		System.out.println(url);
		return url;
	}
	/**
	 * 切换用户个人中心（用户个人中心）查看已预约课程页面
	 * 
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value="persontorder",method=RequestMethod.GET)
	public String topersonTorder(Model model,HttpSession session,HttpServletResponse response,HttpServletRequest request) {
		User u=(User) session.getAttribute("loginUser");
		List<Torder> list;
		int count;
		try {
			list = this.torderServiceImpl.findByUserIdPage(1,Constants.DEFAULT_PAGESIZE,u.getUserId());
			count =(int) this.torderServiceImpl.findCountByUserId(u.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			return "数据库错误界面";
		}

		List<Integer> idList = new ArrayList<Integer>();
		for(int i=0;i<list.size();i++) {
			idList.add(list.get(i).getCourseId());
		}
		//根据课程Id 查询课程Map
		Map<Integer,Course> courseMap = this.courseServiceImpl.findCourseMapByIdList(idList);
		
		List<SuperTorder> superTorder = this.torderServiceImpl.createSuperTorder(courseMap, list);
		
		model.addAttribute("pagecount",count);
		model.addAttribute("page", "1");
		model.addAttribute("yuyue", superTorder);
		return "person_torder";
	}
	/**
	 * 删除用户的预约课程
	 * @param 陈
	 * @param gender
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="delete",method=RequestMethod.GET)
	public String deletecourse(@RequestParam("courseid") int courseid,@RequestParam("page") int pagenum,
								HttpSession session,Model model,Checkbox c) {
		List<Torder> list = null;
		int count = 0;
		User u=(User) session.getAttribute("loginUser");
		try {
			this.torderServiceImpl.deleteByCourseIdUserId(courseid ,u.getUserId());
			list = this.torderServiceImpl.findByUserIdPage(pagenum ,3,u.getUserId());
			count = (int) this.torderServiceImpl.findCountByUserId(u.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Integer> idList = new ArrayList<Integer>();
		for(int i=0;i<list.size();i++) {
			idList.add(list.get(i).getCourseId());
		}
		//根据课程Id 查询课程Map
		Map<Integer,Course> courseMap = this.courseServiceImpl.findCourseMapByIdList(idList);
		
		List<SuperTorder> superTorder = this.torderServiceImpl.createSuperTorder(courseMap, list);
		 model.addAttribute("pagecount",count);
		 model.addAttribute("yuyue", superTorder);
		 model.addAttribute("page", pagenum);
		return "person_torder";
	}
	/**
	 * 批量删除用户的预约课程
	 * @param 陈
	 * @param gender
	 * @return
	 * @throws Exception 
	 */
		@RequestMapping(value="batchdelete",method=RequestMethod.POST)
		public String batchDelete(@RequestParam("page") int pagenum,HttpSession session,Model model,Checkbox c){
			String tempString=c.getTempString();
			// 截取字符串，获得各个checkBox的值
			String temp[] = tempString.split(",");
			
			int count = 0;
			User u=(User) session.getAttribute("loginUser");
			List<Torder> list = null;
			try {
				for(int i=0;i<temp.length;i++){
					this.torderServiceImpl.deleteByCourseIdUserId(Integer.parseInt(temp[i]) ,u.getUserId());	
				}
				list = this.torderServiceImpl.findByUserIdPage(pagenum ,3,u.getUserId());
				count = (int) this.torderServiceImpl.findCountByUserId(u.getUserId());
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
			List<Integer> idList = new ArrayList<Integer>();
			for(int i=0;i<list.size();i++) {
				idList.add(list.get(i).getCourseId());
			}
			//根据课程Id 查询课程Map
			Map<Integer,Course> courseMap = this.courseServiceImpl.findCourseMapByIdList(idList);
			
			List<SuperTorder> superTorder = this.torderServiceImpl.createSuperTorder(courseMap, list);
				model.addAttribute("pagecount",count);
				model.addAttribute("yuyue", superTorder);
				model.addAttribute("page", pagenum);
			return "person_torder";
	}
	/**
	  * 分页查询页码跳转
	  * @param session
	  * @param response
	  * @return
	  */
	 @RequestMapping(value="page",method=RequestMethod.GET)
	 public String page(@RequestParam("page") int pageNume,Model model,HttpSession session) {	
			int count = 0;
			User u=(User) session.getAttribute("loginUser");
			List<Torder> list = null;
			try {
				list=this.torderServiceImpl.findByUserIdPage(pageNume ,Constants.DEFAULT_PAGESIZE,u.getUserId());
			    count = (int) this.torderServiceImpl.findCountByUserId(u.getUserId());
			} catch (Exception e) {
				//TODO 数据库错误
				e.printStackTrace();
			}
			List<Integer> idList = new ArrayList<Integer>();
			for(int i=0;i<list.size();i++) {
				idList.add(list.get(i).getCourseId());
			}
			//根据课程Id 查询课程Map
			Map<Integer,Course> courseMap = this.courseServiceImpl.findCourseMapByIdList(idList);
			
			List<SuperTorder> superTorder = this.torderServiceImpl.createSuperTorder(courseMap, list);
			
			
			model.addAttribute("pagecount",count);
			model.addAttribute("yuyue", superTorder);
			model.addAttribute("page", pageNume);
			return "person_torder";
		 
	 }

}
