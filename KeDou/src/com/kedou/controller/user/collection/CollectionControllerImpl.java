package com.kedou.controller.user.collection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kedou.entity.Collection;
import com.kedou.entity.Course;
import com.kedou.entity.User;
import com.kedou.service.course.CourseServiceImpl;
import com.kedou.service.user.collection.CollectionServiceImpl;
import com.kedou.util.Checkbox;
import com.kedou.util.Constants;

@Controller
@RequestMapping("/collection")
public class CollectionControllerImpl {

	@Resource
	private CollectionServiceImpl collectionServiceImpl;
	@Resource
	private CourseServiceImpl courseServiceImpl;
	/**
	 * 切换个人中心收藏课程页面
	 * 
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value="personcollection",method=RequestMethod.GET)
	public String toCollection(Model model,HttpSession session,HttpServletResponse response,HttpServletRequest request) {
		User u=(User) session.getAttribute("loginUser");
		List<Collection> list;
		int count;
		try {
			list = this.collectionServiceImpl.findByPage(1,Constants.DEFAULT_PAGESIZE,u.getUserId());
			count =(int) this.collectionServiceImpl.findCollectionCount(u.getUserId());
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
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
		model.addAttribute("collection",  courselist);	
		return "person_collect";
	}
	/**
	 * 收藏课程
	 * @param courseId
	 * @return
	 */
	@RequestMapping(value="saveCollection",method=RequestMethod.GET)
	@ResponseBody
	public String saveCollection(@RequestParam("courseid")int courseId) {
		Subject subject = SecurityUtils.getSubject();
		int userid = ((User)subject.getSession().getAttribute("loginUser")).getUserId();
		try {
			this.collectionServiceImpl.saveCollection(new Collection(userid,courseId,new Date()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	/**
	 * 删除收藏课程(在查询课程 的结果页面 点击按钮)
	 * @author 张天润
	 * @param courseId
	 * @return
	 */
	@RequestMapping(value="deleteCollection",method=RequestMethod.GET)
	@ResponseBody
	public String deleteCollection(@RequestParam("courseid")int courseId) {
		Subject subject = SecurityUtils.getSubject();
		int userid = ((User)subject.getSession().getAttribute("loginUser")).getUserId();
		try {
			this.collectionServiceImpl.deleteCollectionByCourseId(courseId, userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "success";
	}
	/**
	 * 删除用户的收藏课程
	 * @param 陈
	 * @param gender
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="delete",method=RequestMethod.GET)
	public String deletecourse(@RequestParam("courseid") int courseid,@RequestParam("page") int pagenum,
								HttpSession session,Model model,Checkbox c) {
		List<Collection> list = null;
		int count = 0;
		User u=(User) session.getAttribute("loginUser");
		try {
			this.collectionServiceImpl.deleteCollectionByCourseId(courseid ,u.getUserId());
			list = this.collectionServiceImpl.findByPage(pagenum ,3,u.getUserId());
			count = (int) this.collectionServiceImpl.findCollectionCount(u.getUserId());
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		List<Integer> params = new ArrayList<Integer>();
		for(int i=0;i<list.size();i++) {
			params.add(list.get(i).getCourseId());
		}
		//根据课程Id 查询课程详情
		List<Course> courselist = this.courseServiceImpl.findCourseList(params);
		 model.addAttribute("pagecount",count);
		 model.addAttribute("collection", courselist);
		 model.addAttribute("page", pagenum);
		return "person_collect";
		
	}
	/**
	 * 批量删除用户的收藏课程
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
		List<Collection> list = null;
			try {
				for(int i=0;i<temp.length;i++){
					this.collectionServiceImpl.deleteCollectionByCourseId(Integer.parseInt(temp[i]) ,u.getUserId());	
				}
				list = this.collectionServiceImpl.findByPage(pagenum ,3,u.getUserId());
				count = (int) this.collectionServiceImpl.findCollectionCount(u.getUserId());
			} catch (Exception e) {
				e.printStackTrace();
			}
				model.addAttribute("pagecount",count);
				model.addAttribute("collection", list);
				model.addAttribute("page", pagenum);
			return "person_collect";
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
				List<Collection> list = null;
				try {
					list=this.collectionServiceImpl.findByPage(pageNume ,Constants.DEFAULT_PAGESIZE,u.getUserId());
				    count = (int) this.collectionServiceImpl.findCollectionCount(u.getUserId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				List<Integer> params = new ArrayList<Integer>();
				for(int i=0;i<list.size();i++) {
					params.add(list.get(i).getCourseId());
				}
				//根据课程Id 查询课程详情
				List<Course> courselist = this.courseServiceImpl.findCourseList(params);
				
				model.addAttribute("pagecount",count);
				model.addAttribute("collection", courselist);
				model.addAttribute("page", pageNume);
				return "person_collect";
		 }
		 
}
