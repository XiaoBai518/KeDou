package com.kedou.controller.course;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.kedou.entity.BusinessCourseTypeRelation;
import com.kedou.entity.Course;
import com.kedou.entity.CourseType;
import com.kedou.service.course.CourseServiceImpl;
import com.kedou.util.UpLoadErro;
import com.kedou.util.UpLoadUtil;


@Controller
@RequestMapping("/course")
public class CourseController {
	@Resource
	private CourseServiceImpl courseServiceImpl;
	@SuppressWarnings("unused")
	private int i = 0;
	
	/**
	 * 前往商家个人店铺首页
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toBusinessHomes", method=RequestMethod.GET)
	public String  toBusinessHomes(@RequestParam("businessId") int id,Model model) throws Exception {
		
		//获得某机构类型列表
		List<BusinessCourseTypeRelation> bct = this.courseServiceImpl.findTypeByBusinessId(id);
		
		//获取某机构热门课程和各类型课程（默认）
		Map<CourseType,List<Course>> courseList = this.courseServiceImpl.courseList(id,bct);
		
		//根据商家课程类型关系 ID 查找课程类型列表
		List<CourseType> courseTypeList  = this.courseServiceImpl.findByIdList(bct);
		
		model.addAttribute("courseList", courseList);
		model.addAttribute("courseTypeList", courseTypeList);
		model.addAttribute("businessId", id);
		
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
	public String  courseListByType(@RequestParam("businessId") int id,@RequestParam("courseTypeId") int courseTypeId,Model model) throws Exception {
		
		//获得某机构类型列表(id,typeId,busId)
		List<BusinessCourseTypeRelation> bct = this.courseServiceImpl.findTypeByBusinessId(id);
		
		//根据商家课程类型关系 ID 查找课程类型列表
		List<CourseType> courseTypeList  = this.courseServiceImpl.findByIdList(bct);
		
		if(courseTypeId == 0){
			//获得热门课程列表
			Map<CourseType, List<Course>> hotCourse = this.courseServiceImpl.gethotCourseLists(id);
			model.addAttribute("courseList", hotCourse);
		}else{
			//获得指定类型下的课程列表
			Map<CourseType, List<Course>> courseList = this.courseServiceImpl.courseListsByCourseType(courseTypeId,id);
			model.addAttribute("courseList", courseList);
		}
		model.addAttribute("courseTypeList", courseTypeList);
		model.addAttribute("businessId", id);
		return "bushomepage";
	}
	/**
	 * 搜索后课程列表页(首页)
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/searchFirstCourseList", method=RequestMethod.GET)
	public String  searchFirstPage(@RequestParam("search") String searchSentence,Model model) throws Exception {
		//获得首页课程列表
		List<Course> courseList = this.courseServiceImpl.findBySearch(searchSentence);
		//获得广告位课程列表
		List<Course> courseAdList = this.courseServiceImpl.findBySearchAd(searchSentence);
		//获得所有符合条件课程
		int courseCount = this.courseServiceImpl.findAllBySearch(searchSentence).size();
		model.addAttribute("cal", courseAdList);
		model.addAttribute("cl", courseList);
		model.addAttribute("totalCount",courseCount);
		model.addAttribute("searchSentence", searchSentence);
		int page = 1;
		model.addAttribute("page", page);
		Gson g = new Gson();
		model.addAttribute("gsonCourseList",g.toJson(courseList));
		return "content";
	}
	
	/**
	 * 搜索后课程列表页(2页以后)
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/searchCourseList", method=RequestMethod.GET)
	public String  searchPage(@RequestParam("page") int page,@RequestParam("totalCount") int totalCount ,@RequestParam("search") String searchSentence,Model model) throws Exception {
		List<Course> courseList = this.courseServiceImpl.findBySearchPage(page, 3, searchSentence);
		List<Course> courseAdList = this.courseServiceImpl.findBySearchAd(searchSentence);
		model.addAttribute("cal", courseAdList);
		model.addAttribute("cl", courseList);
		model.addAttribute("totalCount",totalCount);
		model.addAttribute("page", page);
		model.addAttribute("searchSentence", searchSentence);
		Gson g = new Gson();
		model.addAttribute("gsonCourseList",g.toJson(courseList));
		
		return "content";
	}
	
	/**
	 * 搜索后课程列表页(升序)
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/searchCourseListAsc", method=RequestMethod.GET)
	public String  searchPageAsc(@RequestParam("page") int page,@RequestParam("totalCount") int totalCount ,@RequestParam("search") String searchSentence,Model model) throws Exception {
		List<Course>  courseList = new ArrayList<Course>();
		if(i % 2 == 0){
			courseList = this.courseServiceImpl.findBySearchPageAsc(page, 3, searchSentence);
		}else{
			courseList = this.courseServiceImpl.findBySearchPageDesc(page, 3, searchSentence);
		}
		List<Course> courseAdList = this.courseServiceImpl.findBySearchAd(searchSentence);
		model.addAttribute("cal", courseAdList);
		model.addAttribute("cl", courseList);
		model.addAttribute("totalCount",totalCount);
		model.addAttribute("page", page);
		model.addAttribute("searchSentence", searchSentence);
		Gson g = new Gson();
		model.addAttribute("gsonCourseList",g.toJson(courseList));
		i++;
		return "content";
	}
	/**
	 * 课程图片上传
	 * @param file
	 * @return
	 */
	@RequestMapping(value="/uploadImg",method=RequestMethod.POST,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String uploadImg(@RequestParam("courseImg") MultipartFile file) {
	
    		String uploadResult = UpLoadUtil.uploadImg(file, "course");
    			if(!"-1".equals(uploadResult)) {
    				return new Gson().toJson(uploadResult);
    			}else {
			UpLoadErro e = new UpLoadErro();
			e.setResult(false);
			e.setMessage("写入文件错误");
    				return new Gson().toJson(e);
    			}
	}
//	/**
//	 * 添加课程
//	 */
//	@RequestMapping(value="/addcourse",method=RequestMethod.POST)
//	public String addCourse(@RequestParam(value="courseImg")String courseimg,
//							@RequestParam(value="busId")String busId,
//							@RequestParam(value="courseName")String courseName,
//							@RequestParam(value="description")String description,
//							@RequestParam(value="courseStartTime")String courseStartTime,
//							@RequestParam(value="courseEndTime")String courseEndTime,
//							@RequestParam(value="coursePrice")String coursePrice) {
//		System.out.println(courseimg);
//		System.out.println(busId);
//		System.out.println(courseName);
//		System.out.println(description);
//		System.out.println(courseStartTime);
//		System.out.println(courseEndTime);
//		System.out.println(coursePrice);
//		return "bus_addcourse";
////		try {
////			this.courseServiceImpl.SaveCourse(course);
////		} catch (Exception e) {
////			// TODO 自动生成的 catch 块
////			e.printStackTrace();
////		}
////		return "bus_addcourse";
//	}
	/**
	 * 添加课程
	 */
	@RequestMapping(value="/addcourse",method=RequestMethod.POST)
	public String addCourse(Course course,@RequestParam("startTime")String startTime,
									@RequestParam("endTime")String endTime) {
	
		System.out.println(startTime);
	        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	        try {
	            course.setCourseStartTime(simpleDateFormat.parse(startTime));
	            course.setCourseEndTime(simpleDateFormat.parse(endTime));
	            this.courseServiceImpl.SaveCourse(course);
	        } catch(ParseException px) {
	            px.printStackTrace();
	        } catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} 
	        
		return "bus_addcourse";
	}
}
