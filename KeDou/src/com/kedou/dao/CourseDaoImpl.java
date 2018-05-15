package com.kedou.dao;


import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.Course;
import com.kedou.util.Constants;

@Repository
public class CourseDaoImpl extends BaseDao<Course> {
	@Resource
	private SessionFactory sessionFactory;
	
	/**
	 * @desc 分页查询某商户商品列表
	 * @param code
	 * @return List
	 * @throws Exception
	 */
	public List<Course> courseListByPage(int pageNum) throws Exception{
		return this.find4Page(pageNum, 12);
	}
	
	/**
	 * 
	 * @desc 通过商家id查询热门课程(默认个数)
	 * @author 原源
	 * @createDate 2018年5月10日
	 * @param busId
	 * @param pagesize
	 * @return
	 * @throws Exception
	 */
	public List<Course> courseListByHot(int busId) throws Exception {
		String hql = " from Course  where busId = ? and hot = 1";
		@SuppressWarnings("unchecked")
		Query<Course> query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, busId);
	    query.setMaxResults(Constants.DEFAULT_PAGENUM);  
	    List<Course> hcl = query.list();  
	    return hcl;
	}
	/**
	 * @desc 通过商家id查询热门课程(可选页面展示个数)
	 * @param busId
	 * @param pagesize
	 * @return
	 * @throws Exception
	 */
	public List<Course> courseListByHot(int busId,int pagesize) throws Exception {
		String hql = " from Course  where busId = ? and hot = 1";
		@SuppressWarnings("unchecked")
		Query<Course> query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, busId);
	    query.setMaxResults(pagesize);  
	    List<Course> hcl = query.list();  
	    return hcl;
	}
	
	/**
	 * 
	 * @desc 通过商家id查询热门课程
	 * @author 原源
	 * @createDate 2018年5月10日
	 * @return
	 * @throws Exception
	 */
	public List<Course> courseListByHots(int busId) throws Exception {
		String hql = " from Course  where busId = ? and hot = 1";
		@SuppressWarnings("unchecked")
		Query<Course> query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, busId);
	    List<Course> hcl = query.list();  
	    return hcl;
	}
	
	/**
	 * 
	 * @desc 通过类型id查询课程(默认个数)
	 * @author 原源
	 * @createDate 2018年5月10日
	 * @return
	 * @throws Exception
	 */
	public List<Course> courseListByType(int buscourtypeId) throws Exception{
		String hql = " from CourseTypeRelation  where buscourtypeId = ?";
		Query<Course> query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, buscourtypeId);
	    query.setMaxResults(Constants.DEFAULT_PAGENUM);  
	    List<Course> list = query.list();  
	    return list; 
	}
	/**
	 * @desc 通过类型id查询课程(可选展示个数)
	 * @param buscourtypeId
	 * @param pagesize
	 * @return
	 * @throws Exception
	 */
	public List<Course> courseListByType(int buscourtypeId,int pagesize) throws Exception{
		String hql = " from CourseTypeRelation  where buscourtypeId = ?";
		Query<Course> query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, buscourtypeId);
	    query.setMaxResults(pagesize);  
	    List<Course> list = query.list();  
	    return list; 
	}
	
	/**
	 * 
	 * @desc 通过机构课程列表查询课程列表
	 * @author 原源
	 * @createDate 2018年5月10日
	 * @return
	 * @throws Exception
	 */
	public List<Course> courseListByTypes(int courseId) throws Exception{
			Object [] params = {courseId};
			String hql = " from Course  where courseId = ?";
			return  this.findByProperty(hql, params);
	}
}
