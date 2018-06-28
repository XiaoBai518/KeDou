package com.kedou.dao.course;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.Course;
import com.kedou.entity.CourseBusCtypeRelation;
import com.kedou.util.Constants;

@Repository
public class CourseBusCtypeRelationDaoImpl extends BaseDao<CourseBusCtypeRelation> {

	
	/**
	 * 
	 * @desc 通过类型id查询课程(默认个数)
	 * @author 原源
	 * @createDate 2018年5月10日
	 * @return
	 * @throws Exception
	 */
	public List<Course> courseListByType(int buscourtypeId) throws Exception{
		String hql = " from CourseBusCtypeRelation  where buscourtypeId = ?";
		Query<Course> query = super.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter(0, buscourtypeId);
	    query.setMaxResults(Constants.DEFAULT_PAGESIZE);  
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
		String hql = " from CourseBusCtypeRelation  where buscourtypeId = ?";
		Query<Course> query =  super.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter(0, buscourtypeId);
	    query.setMaxResults(pagesize);  
	    List<Course> list = query.list();  
	    return list; 
	}
	/**
	 * 
	 * @desc 通过机构课程类型id查询课程id
	 * @author 原源
	 * @createDate 2018年5月10日
	 * @return
	 * @throws Exception
	 */
	/**
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public List<CourseBusCtypeRelation> findByBusCourseTypeId(Object [] params) throws Exception {
		String hql = "from CourseBusCtypeRelation as ctr  where ctr.buscourtypeId=?";
		return this.findByProperty(hql, params);
	}
}
