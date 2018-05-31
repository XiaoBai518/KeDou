package com.kedou.dao.calendar;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.kedou.entity.Calendar;


@Repository
public class CalendarDaoImpl {
	@Resource
	private SessionFactory sessionFactory;
	/**
	 * @desc 查询考试的时间
	 * @param 
	 * @return List
	 * @throws Exception
	 */
	public List<Calendar> CalendarList() throws Exception{
		Query query = this.sessionFactory.getCurrentSession().createQuery("from Calendar");
		return query.list();
	}
}
