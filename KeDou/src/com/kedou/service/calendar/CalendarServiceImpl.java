package com.kedou.service.calendar;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.dao.calendar.CalendarDaoImpl;
import com.kedou.entity.Calendar;

@Service
@Transactional(readOnly=true)
public class CalendarServiceImpl {
	@Resource
	private CalendarDaoImpl cdi;
	/**
	 * 
	 * @desc 展示考试信息
	 * @author 康紫云
	 * @createDate 2018年5月29日
	 * @return
	 * @throws Exception
	 */
	public List<Calendar>ShowCalendar(){
		try {
			return this.cdi.CalendarList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	 
	 
}
