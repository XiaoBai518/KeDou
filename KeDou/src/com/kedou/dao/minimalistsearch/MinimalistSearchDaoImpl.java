package com.kedou.dao.minimalistsearch;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.ChooseAnswer;
import com.kedou.entity.ChooseQuestion;
import com.kedou.entity.User;

@Repository
public class MinimalistSearchDaoImpl extends BaseDao<ChooseQuestion>{
	
	/**
	 * 通过问题名称找答案
	 * @param question
	 * @return
	 * @throws Exception
	 */
	public ChooseQuestion findByQuestion(String question) throws Exception{
		SessionFactory sessionFactory = super.getSessionFactory();
		String hql ="from ChooseQuestion where question=:qus";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("qus", question);
		return (ChooseQuestion)query.uniqueResult();
	}
	
	/**
	 * 通过问题ID找答案
	 * @param questionid
	 * @return
	 * @throws Exception
	 */
	public List<ChooseAnswer> findByAnswer(int questionid) throws Exception{
		SessionFactory sessionFactory = super.getSessionFactory();
		String hql ="from ChooseAnswer where questiondes=:quid";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("quid", questionid);
		List<ChooseAnswer> list=query.list();
		return list;
	}
}
