package com.kedou.controller.course;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.kedou.dao.torder.TorderDaoImpl;
import com.kedou.entity.Business;
import com.kedou.entity.BusinessCourseTypeRelation;
import com.kedou.entity.Course;
import com.kedou.entity.CourseBusCtypeRelation;
import com.kedou.entity.CourseType;
import com.kedou.entity.CourseTypeRelation;
import com.kedou.entity.History;
import com.kedou.entity.User;
import com.kedou.service.course.CourseServiceImpl;
import com.kedou.service.torder.TorderServiceImpl;
import com.kedou.superentity.SuperCourse;
import com.kedou.util.Constants;
import com.kedou.util.UpLoadErro;
import com.kedou.util.UpLoadUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;


@Controller
@RequestMapping("/course")
public class CourseController {
	@Resource
	private CourseServiceImpl courseServiceImpl;
	@Resource 
	private TorderServiceImpl torderServiceImpl;
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
		System.out.println("前往商家个人店铺首页");	
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
	 * 前往课程详情页面
	 * @param courseId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="toCourseDetail",method=RequestMethod.GET)
	public String toCourseDetail(@RequestParam("courseId")int courseId,Model model) {
		if(courseId!=0) {
			try {
				Course c = this.courseServiceImpl.findByCourseId(courseId);
				List<CourseType> courseTyperList= this.courseServiceImpl.findCourseTypeListByCourseId(courseId);
				model.addAttribute("course", c);
				model.addAttribute("courseType", courseTyperList);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				model.addAttribute("error", "DBerror");
			}
		}
		
		return "courseDetails";
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
		//获得所有符合条件课程个数
		int courseCount = this.courseServiceImpl.findAllBySearch(searchSentence).size();
		if(courseCount ==0) {
			//TODO 没有搜索结果 给出推荐
			model.addAttribute("totalCount",courseCount);
			model.addAttribute("searchSentence", searchSentence);
			
			model.addAttribute("page", 1);
			model.addAttribute("error", "NoResult");
			return "content";
		}else {
			//获得首页课程列表
			List<Course> courseList = this.courseServiceImpl.findBySearch(searchSentence);
			
			//课程和 课程收藏状态的 组合类
			List<SuperCourse> superCoursesList;
			if(SecurityUtils.getSubject().getSession().getAttribute("loginUser")!=null) {
			 //用户处于登录状态	
				//查看 查询课程是否被收藏
				superCoursesList = this.courseServiceImpl.createSuperCourseByCollection(courseList);
			}else{
				//游客查询
				 superCoursesList = new ArrayList<SuperCourse>();
				//课程默认为 不收藏
				courseList.forEach((c) -> superCoursesList.add(new SuperCourse(c,0)));
			}
			
				//获得广告位课程列表
				List<Course> courseAdList = this.courseServiceImpl.findReCommendCourseByCourseList(courseList);

				//课程和 课程收藏状态的 组合类
				List<SuperCourse> superCoursesAdList;
				if(SecurityUtils.getSubject().getSession().getAttribute("loginUser")!=null) {
				 //用户处于登录状态	
					//查看 查询课程是否被收藏
					superCoursesAdList = this.courseServiceImpl.createSuperCourseByCollection(courseAdList);
				}else{
					//游客查询
					 superCoursesAdList = new ArrayList<SuperCourse>();
					//课程默认为 不收藏
					courseAdList.forEach((c) -> superCoursesAdList.add(new SuperCourse(c,0)));
				}
				model.addAttribute("cal", superCoursesAdList);
		
			
			model.addAttribute("totalCount",courseCount);
			model.addAttribute("searchSentence", searchSentence);
			model.addAttribute("page",1);
			model.addAttribute("cl", superCoursesList);
			
			//商品比较的图表数据
			model.addAttribute("gsonCourseList", new Gson().toJson(this.torderServiceImpl.createSuperChartCourseList(courseList)));
			return "content";
		}
		
	}
	
	/**
	 * 分页跳转
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/searchCourseList", method=RequestMethod.GET)
	public String  searchPage(@RequestParam("page") int page,@RequestParam("totalCount") int totalCount ,@RequestParam("search") String searchSentence,Model model) throws Exception {
				
			//获得page页的课程
			List<Course> courseList = this.courseServiceImpl.findBySearchPage(page,Constants.DEFAULT_PAGESIZE_COURSE,searchSentence);
			
			//课程和 课程收藏状态的 组合类
			List<SuperCourse> superCoursesList;
			if(SecurityUtils.getSubject().getSession().getAttribute("loginUser")!=null) {
			 //用户处于登录状态	
				//查看 查询课程是否被收藏
				superCoursesList = this.courseServiceImpl.createSuperCourseByCollection(courseList);
			}else{
				//游客查询
				 superCoursesList = new ArrayList<SuperCourse>();
				//课程默认为 不收藏
				courseList.forEach((c) -> superCoursesList.add(new SuperCourse(c,0)));
			}
			
			//获得广告位课程列表
//			List<Course> courseAdList = this.courseServiceImpl.findBySearchAd(searchSentence);
			//查看 广告课程是否被收藏
//			List<SuperCourse> superAdCoursesList = this.courseServiceImpl.createSuperCourseByCollection(courseAdList);
//			model.addAttribute("cal", superAdCoursesList);
			
			model.addAttribute("totalCount",totalCount);
			model.addAttribute("searchSentence", searchSentence);
			model.addAttribute("page", page);
			model.addAttribute("cl", superCoursesList);
			model.addAttribute("gsonCourseList", new Gson().toJson(courseList));
			return "content";
		
		
//		List<Course> courseList = this.courseServiceImpl.findBySearchPage(page, 3, searchSentence);
//		List<Course> courseAdList = this.courseServiceImpl.findBySearchAd(searchSentence);
//		model.addAttribute("cal", courseAdList);
//		model.addAttribute("cl", courseList);
//		model.addAttribute("totalCount",totalCount);
//		model.addAttribute("page", page);
//		model.addAttribute("searchSentence", searchSentence);
//		Gson g = new Gson();
//		model.addAttribute("gsonCourseList",g.toJson(courseList));
//		
//		return "content";
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
		List<SuperCourse> superCoursesList;
		if(i % 2 == 0){
			courseList = this.courseServiceImpl.findBySearchPageAsc(page, 3, searchSentence);
			//课程和 课程收藏状态的 组合类
			if(SecurityUtils.getSubject().getSession().getAttribute("loginUser")!=null) {
			 //用户处于登录状态	
				//查看 查询课程是否被收藏
				superCoursesList = this.courseServiceImpl.createSuperCourseByCollection(courseList);
			}else{
				//游客查询
				 superCoursesList = new ArrayList<SuperCourse>();
				//课程默认为 不收藏
				courseList.forEach((c) -> superCoursesList.add(new SuperCourse(c,0)));
			}
			
		}else{
			courseList = this.courseServiceImpl.findBySearchPageDesc(page, 3, searchSentence);
			//课程和 课程收藏状态的 组合类
			if(SecurityUtils.getSubject().getSession().getAttribute("loginUser")!=null) {
			 //用户处于登录状态	
				//查看 查询课程是否被收藏
				superCoursesList = this.courseServiceImpl.createSuperCourseByCollection(courseList);
			}else{
				//游客查询
				 superCoursesList = new ArrayList<SuperCourse>();
				//课程默认为 不收藏
				courseList.forEach((c) -> superCoursesList.add(new SuperCourse(c,0)));
			}
			
		}
		//TODO 广告 推荐  没写
		//广告 推荐 
//		List<Course> courseAdList = this.courseServiceImpl.findBySearchAd(searchSentence);
//		model.addAttribute("cal", courseAdList);
		model.addAttribute("cl", superCoursesList);
		model.addAttribute("totalCount",totalCount);
		model.addAttribute("page", page);
		model.addAttribute("searchSentence", searchSentence);
		model.addAttribute("gsonCourseList",new Gson().toJson(courseList));
		
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
	/**
	 * 添加课程
	 */
	@RequestMapping(value="/addcourse",method=RequestMethod.POST)
	public String addCourse(Course course,@RequestParam("startTime")String startTime,
									@RequestParam("endTime")String endTime,
									@RequestParam("courseType")int typeid,
									@RequestParam(value="address",required=false)String address) {

		
		
		//时间格式化工具
	        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	        try {
	        	//设置课程开始和结束时间
	            course.setCourseStartTime(simpleDateFormat.parse(startTime));
	            course.setCourseEndTime(simpleDateFormat.parse(endTime));
	            //保存到数据库
	            this.courseServiceImpl.SaveCourse(course);
	            	//课程ID
	            	int courseid = course.getCourseId();
	            	//机构Id
	            	Business bus = (Business)SecurityUtils.getSubject().getSession().getAttribute("loginBusiness");
	            	int busid = bus.getBusId();
	            	
				//设置课程和课程类型的关系
	            	CourseTypeRelation ctr = new CourseTypeRelation();
	            	ctr.setCourseId(courseid);
	            	ctr.setCoursetypeId(typeid);
	            	this.courseServiceImpl.SaveCourseTypeRelation(ctr);
	    		
	    		//设置课程 和 商家课程类型映射  的关系
	    			CourseBusCtypeRelation cbctr = new CourseBusCtypeRelation();
	    			cbctr.setCourseId(course.getCourseId());
	    		  //获取商家课程类型关系ID
	    			int buscourtypeId= this.courseServiceImpl.findBusCTypeIdByCourseTypeIdAndBusId(typeid, busid);
	    			cbctr.setBuscourtypeId(buscourtypeId);
	    			this.courseServiceImpl.SaveCourseBusCtypeRelation(cbctr);
	        } catch(ParseException px) {
	            px.printStackTrace();
	        } catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} 
	        
		return "bus_addcourse";
	}
	/**
	 * 前往课程列表页（商家个人中心）
	 * @return
	 */
	@RequestMapping(value="/tocourselist",method=RequestMethod.GET)
	public String toCourseList(@RequestParam("pagenum")int pageNum,Model model) {
		Business bus = (Business)SecurityUtils.getSubject().getSession().getAttribute("loginBusiness");
		List<Course> list = null;
		List<SuperCourse> supercourselist = null;
		int count = 0;
		try {
			list = this.courseServiceImpl.findAllCourseByBusId(bus.getBusId(), pageNum);
			supercourselist = this.courseServiceImpl.findTypeByCourseList(list);
			count = (int) this.courseServiceImpl.findCourseCount(bus.getBusId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("Scourselist", supercourselist);
		model.addAttribute("page", 1);
		model.addAttribute("pagecount",count);
		return "bus_courselist";
	}
	/**
	  * 分页查询页码跳转
	  * @param session
	  * @param response
	  * @return
	  */
	 @RequestMapping(value="bypage",method=RequestMethod.GET)
	 public String page(@RequestParam("page") int pageNum,Model model) {
		 Business bus = (Business)SecurityUtils.getSubject().getSession().getAttribute("loginBusiness");
			List<Course> list = null;
			List<SuperCourse> supercourselist = null;
			int count = 0;
			try {
				list = this.courseServiceImpl.findAllCourseByBusId(bus.getBusId(), pageNum);
				supercourselist = this.courseServiceImpl.findTypeByCourseList(list);
				count = (int) this.courseServiceImpl.findCourseCount(bus.getBusId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			model.addAttribute("pagecount",count);
			model.addAttribute("Scourselist", supercourselist);
			model.addAttribute("page", pageNum);
			return "person_history";
	 }
	 @ResponseBody
	 @RequestMapping(value="/autoComplete",method=RequestMethod.GET,produces = "application/json; charset=utf-8")
	 public String autoComplete(@RequestParam("query")String query) {
		
		 List<String> result = this.courseServiceImpl.autoComplete(query);


	        JSONObject jsonObj = new JSONObject();//创建json格式的数据
	        JSONArray jsonArr = new JSONArray();//json格式的数组
	        Gson g = new Gson();
	        
	        
	        try {
		        for (int i = 0; i < result.size(); i++) {
		        		if(result.get(i).contains(query)) {
		                JSONObject jsonObjArr = new JSONObject();
		                jsonObjArr.put("value", result.get(i));
		                jsonArr.put(jsonObjArr);
		        		}
		        } 
		        jsonObj.put("suggestions", jsonArr);
		        jsonObj.put("query", query);
	        }
	        catch (JSONException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
	        }
	        System.out.println(jsonObj.toString());
	        return jsonObj.toString();
	}

}
