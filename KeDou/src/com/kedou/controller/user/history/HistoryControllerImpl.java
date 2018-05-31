package com.kedou.controller.user.history;

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
import com.kedou.entity.History;
import com.kedou.entity.User;
import com.kedou.service.course.CourseServiceImpl;
import com.kedou.service.user.history.HistoryServiceImpl;
import com.kedou.util.Constants;

@Controller
@RequestMapping("/userhistory")
public class HistoryControllerImpl {
	@Resource
	private HistoryServiceImpl historyServiceImpl;
	@Resource
	private CourseServiceImpl courseServiceImpl;
	
	/**
	 * 切换个人中心浏览课程历史页面
	 * 
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value="personhistory",method=RequestMethod.GET)
	public String toHistory(Model model,HttpSession session,HttpServletResponse response,HttpServletRequest request) {
		User u=(User) session.getAttribute("loginUser");
		List<History> list;
		int count;
		
		try {
			list = this.historyServiceImpl.findByPageHistory(1,Constants.DEFAULT_PAGESIZE,u.getUserId());
			count =(int) this.historyServiceImpl.findHistoryCount(u.getUserId());
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
		model.addAttribute("courselist",courselist);	
		return "person_history";
	}
	/**
	 * 删除用户的浏览课程历史
	 * @param 陈
	 * @param gender
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="delete",method=RequestMethod.GET)
	public String deletecourse(@RequestParam("courseid") int courseid,@RequestParam("page") int pagenum,
								HttpSession session,Model model,Checkbox c) {	
		List<History> list = null;
		int count = 0;
		User u=(User) session.getAttribute("loginUser");
		try {
			this.historyServiceImpl.deleteHistoryByCourseId(courseid ,u.getUserId());
			list = this.historyServiceImpl.findByPageHistory(pagenum ,3,u.getUserId());
			count = (int) this.historyServiceImpl.findHistoryCount(u.getUserId());
		   
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		 model.addAttribute("pagecount",count);
		 model.addAttribute("history", list);
		 model.addAttribute("page", pagenum);

		return "person_history";
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
			List<History> list = null;
			try {
				for(int i=0;i<temp.length;i++){
					this.historyServiceImpl.deleteHistoryByCourseId(Integer.parseInt(temp[i]) ,u.getUserId());	
				}
				list = this.historyServiceImpl.findByPageHistory(pagenum ,3,u.getUserId());
				count = (int) this.historyServiceImpl.findHistoryCount(u.getUserId());
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
				model.addAttribute("pagecount",count);
				model.addAttribute("history", list);
				model.addAttribute("page", pagenum);
			return "person_history";
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
				List<History> list = null;
				try {
					list=this.historyServiceImpl.findByPageHistory(pageNume ,Constants.DEFAULT_PAGESIZE,u.getUserId());
				    count = (int) this.historyServiceImpl.findHistoryCount(u.getUserId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				model.addAttribute("pagecount",count);
				model.addAttribute("history", list);
				model.addAttribute("page", pageNume);
				return "person_history";
		 }
		 
	
}
