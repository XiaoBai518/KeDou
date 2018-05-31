package com.kedou.controller.torder;

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

import com.kedou.entity.Checkbox;
import com.kedou.entity.Course;
import com.kedou.entity.Torder;
import com.kedou.entity.User;
import com.kedou.service.course.CourseServiceImpl;
import com.kedou.service.torder.TorderServiceImpl;
import com.kedou.util.Constants;

@Controller
@RequestMapping("/torder")
public class TorderController {
	@Resource
	private TorderServiceImpl torderServiceImpl;
	@Resource
	private CourseServiceImpl courseServiceImpl;
	
	/**
	 * 切换个人中心预约课程页面
	 * 
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value="persontorder",method=RequestMethod.GET)
	public String toTorder(Model model,HttpSession session,HttpServletResponse response,HttpServletRequest request) {
		User u=(User) session.getAttribute("loginUser");
		List<Torder> list;
		int count;
		try {
			list = this.torderServiceImpl.findByPage(1,Constants.DEFAULT_PAGESIZE,u.getUserId());
			count =(int) this.torderServiceImpl.findCountByUserId(u.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			return "数据库错误界面";
		}

		List<Integer> params = new ArrayList<Integer>();
		for(int i=0;i<list.size();i++) {
			params.add(list.get(i).getCourseId());
		}
		//根据课程Id 查询课程详情
		List<Course> courselist = this.courseServiceImpl.findCourseList(params);
		
		model.addAttribute("pagecount",count);
		model.addAttribute("page", "1");
		model.addAttribute("yuyue", courselist);
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
			list = this.torderServiceImpl.findByPage(pagenum ,3,u.getUserId());
			count = (int) this.torderServiceImpl.findCountByUserId(u.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		 model.addAttribute("pagecount",count);
		 model.addAttribute("yuyue", list);
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
				list = this.torderServiceImpl.findByPage(pagenum ,3,u.getUserId());
				count = (int) this.torderServiceImpl.findCountByUserId(u.getUserId());
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
				model.addAttribute("pagecount",count);
				model.addAttribute("yuyue", list);
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
				list=this.torderServiceImpl.findByPage(pageNume ,Constants.DEFAULT_PAGESIZE,u.getUserId());
			    count = (int) this.torderServiceImpl.findCountByUserId(u.getUserId());
				
			} catch (Exception e) {

				e.printStackTrace();
			}
			model.addAttribute("pagecount",count);
			model.addAttribute("yuyue", list);
			model.addAttribute("page", pageNume);
			return "person_torder";
		 
	 }

}
