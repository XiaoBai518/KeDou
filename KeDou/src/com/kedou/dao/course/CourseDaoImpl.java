package com.kedou.dao.course;


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
	    query.setMaxResults(Constants.DEFAULT_PAGESIZE);  
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
	/**
	 * 
	 * @desc 通过搜索内容查询课程列表
	 * @author 原源
	 * @createDate 2018年5月10日
	 * @return
	 * @throws Exception
	 */
	public List<Course> findBySearchSentencePage(int pageNum,int pageSize,String searchSentence){
		String hql = " from Course  where courseName like ?";
		Query query=this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, "%"+searchSentence+"%");
		query.setFirstResult((pageNum-1)*pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}
	
	/**
	 * 
	 * @desc 通过搜索内容查询课程列表（升序）
	 * @author 原源
	 * @createDate 2018年5月10日
	 * @return
	 * @throws Exception
	 */
	public List<Course> findBySearchSentencePageAsc(int pageNum,int pageSize,String searchSentence){
		String hql = " from Course  where courseName like ? order by coursePrice asc";
		Query query=this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, "%"+searchSentence+"%");
		query.setFirstResult((pageNum-1)*pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}
	
	/**
	 * 
	 * @desc 通过搜索内容查询课程列表(降序)
	 * @author 原源
	 * @createDate 2018年5月10日
	 * @return
	 * @throws Exception
	 */
	public List<Course> findBySearchSentencePageDesc(int pageNum,int pageSize,String searchSentence){
		String hql = " from Course  where courseName like ? order by coursePrice desc";
		Query query=this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, "%"+searchSentence+"%");
		query.setFirstResult((pageNum-1)*pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}
	
	/**
	 * 
	 * @desc 通过搜索内容查询课程列表(首页)
	 * @author 原源
	 * @createDate 2018年5月10日
	 * @return
	 * @throws Exception
	 */
	public List<Course> findBySearchSentence(String searchSentence){
		String hql = " from Course  where courseName like ?";
		Query query=this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, '%'+searchSentence+'%');
		query.setMaxResults(3);
		return query.list();
	}
	
	/**
	 * 
	 * @desc 通过搜索内容查询课程列表(所有)
	 * @author 原源
	 * @createDate 2018年5月10日
	 * @return
	 * @throws Exception
	 */
	public List<Course> findAllBySearchSentence(String searchSentence){
		String hql = " from Course  where courseName like ?";
		Query query=this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, "%"+searchSentence+"%");
		return query.list();
	}
	
	/**
	 * 
	 * @desc 通过搜索内容查询课程列表(广告位)
	 * @author 原源
	 * @createDate 2018年5月10日
	 * @return
	 * @throws Exception
	 */
	public List<Course> findBySearchSentenceAd(String searchSentence){
		String hql = " from Course  where courseName like ? and hot = 1";
		Query query=this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, "%"+searchSentence+"%");
		query.setMaxResults(3);
		return query.list();
	}
	/**
	 * 根据课程ID列表查询课程列表
	 * @param courseid
	 * @return
	 */
	public List<Course> findCourseList(List<Integer> params) {	
		String hql = "from Course  where courseId in (:courseid)  ";  
		 return  (List<Course>) super.getSessionFactory().getCurrentSession().createQuery(hql).setParameterList("courseid", params).list();
	            	
	}
	/**
	 * 按id查询并锁定相应的商家
	 * @param id
	 * @throws Exception
	 */
		public void lockCou(int id)throws Exception{
			SessionFactory sessionFactory =super.getSessionFactory();
			String hql="update Course cou set cou.state=:state where cou.courseId=:id";
			Query query=sessionFactory.getCurrentSession().createQuery(hql);
			query.setParameter("state", 2);
			query.setParameter("id", id);
			query.executeUpdate();
			
		}
}
