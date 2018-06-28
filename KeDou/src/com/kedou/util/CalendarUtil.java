package com.kedou.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarUtil {

	/***
	 * 获得本周七天的日期List
	 * @return
	 */
	public static List<Date> getCurrentWeekDayList() {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int temp = 0;
		if(cal.get(Calendar.DAY_OF_WEEK)==1) {
			temp = -6;
		}else {
			temp = 2 - cal.get(Calendar.DAY_OF_WEEK);
		}
		cal.add(Calendar.DAY_OF_WEEK, temp);	
		List<Date>  dateList = new ArrayList<Date>(); 
			for(int i=0;i<7;i++) {
				Date date = cal.getTime();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				
				try {
					dateList.add(format.parse(format.format(date)));
				} catch (ParseException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				
				cal.add(cal.DATE,1);
			}
			return dateList;
	}
}
