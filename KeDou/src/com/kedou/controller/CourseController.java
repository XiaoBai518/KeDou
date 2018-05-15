package com.kedou.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kedou.entity.BusinessCourseTypeRelation;
import com.kedou.entity.Course;
import com.kedou.entity.CourseType;
import com.kedou.service.CourseServiceImpl;


@Controller
@RequestMapping("/course")
public class CourseController {
	@Resource
	private CourseServiceImpl courseServiceImpl;
	
	/**
	 * 前往商家个人店铺首页
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toBusinessHomes", method=RequestMethod.GET)
	public String  toBusinessHomes(@RequestParam("businessId") int id,HttpServletRequest request) throws Exception {
		
		//获得某机构类型列表
		List<BusinessCourseTypeRelation> bct = this.courseServiceImpl.findTypeByBusinessId(id);
		
		//获取某机构热门课程和各类型课程（默认）
		Map<CourseType,List<Course>> courseList = this.courseServiceImpl.courseList(id,bct);
		
		//根据商家课程类型关系 ID 查找课程类型列表
		List<CourseType> courseTypeList  = this.courseServiceImpl.findByIdList(bct);
		
		request.setAttribute("courseList", courseList);
		request.setAttribute("courseTypeList", courseTypeList);
		request.setAttribute("businessId", id);
		
		return "bushomepage";
	}
	
	/**
	 * 查询更多类型的商品
	 * @param id
	 * @param courseTypeId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/TypeCoursemore", method=RequestMethod.GET)
	public String  courseListByType(@RequestParam("businessId") int id,@RequestParam("courseTypeId") int courseTypeId,HttpServletRequest request) throws Exception {
		
		//获得某机构类型列表(id,typeId,busId)
		List<BusinessCourseTypeRelation> bct = this.courseServiceImpl.findTypeByBusinessId(id);
		
		//根据商家课程类型关系 ID 查找课程类型列表
		List<CourseType> courseTypeList  = this.courseServiceImpl.findByIdList(bct);
		
		if(courseTypeId == 0){
			//获得热门课程列表
			Map<CourseType, List<Course>> hotCourse = this.courseServiceImpl.gethotCourseLists(id);
			request.setAttribute("courseList", hotCourse);
		}else{
			//获得指定类型下的课程列表
			Map<CourseType, List<Course>> courseList = this.courseServiceImpl.courseListsByCourseType(courseTypeId,id);
			request.setAttribute("courseList", courseList);
		}
		request.setAttribute("courseTypeList", courseTypeList);
		request.setAttribute("businessId", id);
		return "bushomepage";
	}

}
