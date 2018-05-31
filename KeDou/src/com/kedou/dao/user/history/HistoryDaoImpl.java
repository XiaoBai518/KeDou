package com.kedou.dao.user.history;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.History;

@Repository
public class HistoryDaoImpl extends BaseDao<History> {

	/**
	    * 
		* @desc 通过用户ID查询访问历史课程
		* @author 陈
		* @createDate
		* @return User
		* @throws Exception
		*/
	public List<History> findHistoryByUserId(int userid) throws Exception{
		SessionFactory sessionFactory = super.getSessionFactory();
		String hql ="from History where userId = :id";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", userid);
		return query.list();
	}
	/**
	    * 
		* @desc 通过课程ID和用户Id删除历史课程
		* @author 陈
		* @createDate
		* @return User
		* @throws Exception
		*/
	public void deleteHistoryByCourseId(int courseid, int userid) throws Exception{
		SessionFactory sessionFactory = super.getSessionFactory();
		String hql ="delete History  where courseId = :id and userId = :userid";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", courseid);
		query.setParameter("userid", userid);
		query.executeUpdate();
	}
	/**
	    * 
		* @desc 浏览历史课程分页查询
		* @author 陈
		* @createDate
		* @return User
		* @throws Exception
		*/
	public List<History> findByPageHistory(int pageNum, int pageSize,Object[] params) throws Exception {
 		String hql ="from History where userId=?";
		return super.find4PageByProperty(pageNum, pageSize, hql, params);
	}
	 /**
	    * 
		* @desc 根据用户id查询其历史访问课程的总数
		* @author 陈
		* @createDate
		* @return User
		* @throws Exception
		*/
	public long findCountByUserId(int userid) throws Exception {
		SessionFactory sessionFactory = super.getSessionFactory();
		String hql ="select count(*) from History where userId= :userid";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userid", userid);
		return (long)query.uniqueResult();
	}
}
