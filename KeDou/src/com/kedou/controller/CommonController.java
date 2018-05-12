package com.kedou.controller;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kedou.service.UserServiceImpl;
import com.kedou.service.common.CommonServiceImpl;


@Controller
@RequestMapping("/common")
public class CommonController {
	
	@Resource
	private CommonServiceImpl commonServiceImpl;
	@Resource
	private UserServiceImpl userServiceImpl;
	
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
	 * 验证码是否正确
	 * @param verify
	 * @param session
	 * @return 验证码正确 返回 1  否则 返回 -1
	 */
	@RequestMapping(value="/istrue", method=RequestMethod.GET)
	@ResponseBody
	public String istrue(@RequestParam("verifyCode")String verify,HttpSession session) {
		String origincode = (String) session.getAttribute("verifyCode");
		if(origincode.equalsIgnoreCase(verify)) {
			//验证码正确 返回1
			return "1";
			
		}else {
			//验证码错误 返回-1
			return "-1";
		}
	}
	/**
	 * 前往首页
	 * @return
	 */
	@RequestMapping(value="/toindex", method=RequestMethod.GET)
	public String toIndex() {
		return "index";
	}


}
