package com.kedou.dao.visitNumber;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.BusinessVisitNumber;

@Repository
public class VisitNumberDaoImpl extends BaseDao<BusinessVisitNumber> {

	/**
	 * 查找表中最后一条数据
	 * @return
	 * @throws Exception
	 */
	public BusinessVisitNumber findLast() throws Exception {
		String hql = "from BusinessVisitNumber order by id desc ";
		 Query query = super.getSessionFactory().getCurrentSession().createQuery(hql);
		 query.setMaxResults(1);  
		 return (BusinessVisitNumber)query.uniqueResult();
		
	}
	
	public List<BusinessVisitNumber> getVisitNumberByDate (Date date) throws Exception {
		 String hql = "from BusinessVisitNumber where date = :Date";
		 Query query = super.getSessionFactory().getCurrentSession().createQuery(hql);
		 	query.setDate("Date", date);
		 return query.list();
	}
}
