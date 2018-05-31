package com.kedou.dao.user.collection;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.Collection;

@Repository
public class CollectionDaoImpl extends BaseDao<Collection> {
	/**
	    * 
		* @desc 根据用户id查询其收藏课程的总数
		* @author 陈
		* @createDate
		* @return User
		* @throws Exception
		*/
	public long findCollectCount(int userid) throws Exception {
		String hql ="select count(*) from Collection where userId= :userid";
		Query query=super.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter("userid", userid);
		return (long)query.uniqueResult();
	}
	/**
	    * 
		* @desc 通过用户ID查收藏课程
		* @author 陈
		* @createDate
		* @return User
		* @throws Exception
		*/
	public List<Collection> findCollectionByUserId(int userid) throws Exception{
		SessionFactory sessionFactory = super.getSessionFactory();
		String hql ="from Collection where userId = :id";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", userid);
		return query.list();
	}
	/**
	    * 
		* @desc 通过课程ID和用户Id删除收集课程
		* @author 陈
		* @createDate
		* @return User
		* @throws Exception
		*/
	public void deleteCollectionByCourseId(int courseid, int userid) throws Exception{
		SessionFactory sessionFactory = super.getSessionFactory();
		String hql ="delete Collection  where courseId = :id and userId = :userid";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", courseid);
		query.setParameter("userid", userid);
		query.executeUpdate();
	}
	/**
	    * 
		* @desc 收藏课程分页查询 (暂时未用到)
		* @author 陈
		* @createDate
		* @return User
		* @throws Exception
		*/
 public List<Collection> findByPage(int pageNum, int pageSize,Object [] params) throws Exception {
 	
 	String hql ="from Collection where userId= ?";
 
		return super.find4PageByProperty(pageNum, pageSize, hql, params);
	}
}
