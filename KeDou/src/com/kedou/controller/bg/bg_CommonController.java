package com.kedou.controller.bg;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import com.google.gson.Gson;


import com.kedou.entity.Business;
import com.kedou.entity.Course;
import com.kedou.entity.Torder;
import com.kedou.entity.User;
import com.kedou.service.bg.AdminServiceImpl;
import com.kedou.service.bg.ReservationServiceImpl;
import com.kedou.service.bg.StoreServiceImpl;
import com.kedou.service.course.CourseServiceImpl;


@Controller
@RequestMapping("/bg_common")
public class bg_CommonController {
	@Resource
	private AdminServiceImpl adminServiceImp;
	@Resource
	private StoreServiceImpl storeServiceImpl;
	@Resource
	private ReservationServiceImpl resevationServiceImpl;
	@Resource
	private CourseServiceImpl courseServiceImpl;
	
	@RequestMapping(value="/tobglogin")
	private String openview() {
		return "bg_login";
	}
	/**
	 * 该controller为基本页面的跳转
	 */
	
	@RequestMapping(value="/bggoAddBulltein")
	public String goAddBulltein() {
		return "bg_addBulltein";
	}
	
	/**
	 * 跳转到编辑课程页面
	 * @return
	 */
	@RequestMapping(value="/bggoEditStore")
	public String goEditStore() {
		return "bg_editStore";
	}
	/**
	 * 检索数据库中所有预约 储存到JSON 并跳转到课程查看页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/bggoSearchCourse")
	public String goSearchCourse(Model model) {
		List <Course> list=courseServiceImpl.searchAllCou();
		Gson g=new Gson();
		model.addAttribute("list", null);
		model.addAttribute("list", list);
		
		return "bg_searchCourse";
	}
	
	/**
	 * 检索数据库中所有预约 储存到JSON 并跳转到预约查看页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="bggoSearchReservation")
	public String goSearchReservation(Model model) {
		List <Torder> list=resevationServiceImpl.searchAllTorder();
		Gson g=new Gson();
		model.addAttribute("list", null);
		model.addAttribute("list", list);
		
		return "bg_searchReservation";
	}
	
	/**
	 * 检索数据库中所有商家 储存到JSON 并跳转到商家查看页面
	 * @return
	 */
	@RequestMapping(value="bggoSearchStore")
	public String goSerchStore(Model model) {
		List <Business> list=storeServiceImpl.searchAllBus();
		Gson g=new Gson();
		model.addAttribute("list", null);
		model.addAttribute("list", list);
		
		return "bg_searchStore";
	}
	
	
	/**
	 * 检索数据库中的所有用户 储存到json 并跳转到用户查看页面
	 * @return
	 */
	@RequestMapping(value="/bggoSearchUser")
	public String goSearchUser(Model model) {
		List <User> list=adminServiceImp.searchAllUser();
		Gson g=new Gson();
		model.addAttribute("list", null);
		model.addAttribute("list", list);
		
		return "bg_searchUser";
	}
	
	
	
	/**
	 * 跳转到添加管理员页面
	 * @author 宋亚楼
	 * @return bg_addAdmin
	 */
	@RequestMapping(value="/bggoAddAdmin")
	public String goAddAdmin() {
		return "bg_addAdmin";
	}
	/**
	 * 跳转到编辑用户页面
	 * @author 宋亚楼
	 * @return
	 */
	@RequestMapping(value="/bggoEditUser")
	public String goEditUser() {
		return "bg_editUser";
	}
	


}
