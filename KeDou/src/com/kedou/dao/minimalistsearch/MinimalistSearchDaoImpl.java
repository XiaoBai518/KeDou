package com.kedou.dao.minimalistsearch;


import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.ChooseQuestion;

@Repository
public class MinimalistSearchDaoImpl extends BaseDao<ChooseQuestion>{
	
	public int findNextId(int questionId) {
		
		String hql = "select nextId from ChooseSequence where questionId = :questionid";
		Query query = super.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter("questionid", questionId);
		
		return (int)query.uniqueResult();
	}
}
