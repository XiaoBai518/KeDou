package com.kedou.controller.bg;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.kedou.entity.Course;
import com.kedou.service.course.CourseServiceImpl;

@Controller
@RequestMapping("/bg_course")
public class bg_CourseController {
	@Resource
	private CourseServiceImpl courseServiceImpl;
	
	/**
	 * 锁定选中的商家
	 * @param str
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/bgLockCou",method=RequestMethod.POST)
	@ResponseBody
	public String lockCourse(@RequestParam("str")String str,HttpServletRequest request) {
		
		if(str!=null) {
		Gson g = new Gson();
		
		 List<Course> ul = g.fromJson(str, new TypeToken<List<Course>>() {}.getType());//对于不是类的情况，用这个参数给出)
		 int temp=0;
		 int[] array=new int[ul.size()];
		for(Course c:ul) {
			array[temp]=c.getCourseId();
			temp++;
		}
		courseServiceImpl.lockCou(array);
		}else {
			
		}
		
		return null;
		
	}
}
