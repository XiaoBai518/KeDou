package com.kedou.controller.calendar;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kedou.entity.Calendar;
import com.kedou.service.calendar.CalendarServiceImpl;

@Controller
@RequestMapping("/calendar")
public class CalendarController {
	@Resource
	private CalendarServiceImpl csi;
	/**
	 * 展示考试时间 
	 * @author 康紫云
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String Show(HttpServletRequest request){
		List<Calendar> c=this.csi.ShowCalendar();
//      测试是否获取到数据库数据		
//		for(Calendar ci : c){
//			System.out.println(ci.toString());
//		}
		request.setAttribute("calendar", c);
		return "calendar";
		
	}
}
