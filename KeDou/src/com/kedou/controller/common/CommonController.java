package com.kedou.controller.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.kedou.entity.BusinessVisitNumber;
import com.kedou.entity.Course;
import com.kedou.entity.User;
import com.kedou.service.common.CommonServiceImpl;
import com.kedou.service.search.SearchServiceImpl;
import com.kedou.service.user.UserServiceImpl;
import com.kedou.service.visitNumber.VisitNumberServiceImpl;


@Controller
@RequestMapping("/common")
public class CommonController {
	
	@Resource
	private CommonServiceImpl commonServiceImpl;
	@Resource
	private UserServiceImpl userServiceImpl;
	@Resource(name="springcacheManager")
	private EhCacheCacheManager cacheManager;
	@Resource
	private VisitNumberServiceImpl visitNumberServiceImpl;
	
	 private static Logger logger = Logger.getLogger(CommonController.class);

	/**
	 * 获取验证码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/validatecade", method=RequestMethod.GET)
	@ResponseBody
	public void ValidateCode(HttpServletRequest request,HttpServletResponse response) {
		 this.commonServiceImpl.ValidateCode(request, response);
		
	}
	/**
	 * 访问量
	 * @return
	 */
	@RequestMapping(value="/visitTest",method=RequestMethod.GET)
	public String Visittest() {
		Cache cache = cacheManager.getCache("visitNumberCache");
		if(cache.get("visitNumberMap")!=null) {
			Map<Integer,BusinessVisitNumber> bvn = (Map<Integer,BusinessVisitNumber>)cache.get("visitNumberMap").get();
			try {
//				this.visitNumberServiceImpl.UpdateMap(bvn);
			} catch (Exception e) {
				//更新操作异常
				e.printStackTrace();
			}
			
			//刷新缓存
			cache.put("visitNumberMap", bvn);
		  }
		return "index";
	}
	/**
	 * 验证码是否正确
	 * @param verify
	 * @param session
	 * @return 验证码正确 返回 1  否则 返回 -1
	 */
	@RequestMapping(value="/istrue", method=RequestMethod.GET)
	@ResponseBody
	public String istrue(@RequestParam("verifyCode")String verify,HttpSession session) {
		String origincode = (String) session.getAttribute("verifyCode");
		if(origincode!=null&&verify!=null) {
			if(origincode.equalsIgnoreCase(verify)) {
				//验证码正确 返回1
				return "1";
				
			}else {
				//验证码错误 返回-1
				return "-1";
			}
		}
		return "-1";
	}
	/**
	 * 前往首页
	 * @return
	 */
	@RequestMapping(value="/toindex", method=RequestMethod.GET)
	public String toIndex(HttpSession session) {
		
		if(session.getAttribute("userAddress")!=null) {
			return "index";
		}else {
			return "useraddress";
		}
	}
	
	@RequestMapping(value="/ajaxtest",method=RequestMethod.GET,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String test(@RequestParam("log")String log) {
		User user = (User)SecurityUtils.getSubject().getSession().getAttribute("loginUser");
		int userId=0;
		if(user!=null) {
			userId = user.getUserId();
		}else{
			userId = 0;
		}
		log = log.substring(0, log.length()-1)+",\"userId\":"+userId+"}";
		logger.info(log);
		return "123123123";
		
	}
	@RequestMapping(value="/noRole",method=RequestMethod.GET)
	public String noRole() {
		return "noRole";
	}
	
//	@RequestMapping(value="/ajaxtest", method=RequestMethod.GET,produces = "application/json; charset=utf-8")
//	@ResponseBody
//	public String test() {
//	
//		Map<Course, User> course = new HashMap<>();
//		Course c = new Course();
//		c.setCourseId(1);
//		c.setCourseName("啦啦啦");
//		
//		User u = new User();
//		u.setUserId(2);
//		u.setUserName("哈哈");
//		course.put(c, u);
//		
//		Gson g = new Gson();
//		return g.toJson(course);
//		
//	}



}
