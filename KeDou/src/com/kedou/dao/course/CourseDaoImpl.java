package com.kedou.dao.course;


import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.Course;
import com.kedou.entity.Recommend;
import com.kedou.util.Constants;

@Repository
public class CourseDaoImpl extends BaseDao<Course> {
	

	/**
	 * 根据 课程Id 查询推荐课程Id
	 * @param ids
	 * @return
	 */
	public List<Integer> findReCommendCourseId(List<Integer> idList) {
		String hql = " select recourseid from Recommend where courseid in (:idList) and score <> null order by score DESC";
		Query query = super.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameterList("idList", idList);
		query.setMaxResults(Constants.RECOMMEND_SHOWSIZE);
		
		return query.list();
		
		
	}
	/**
	 * 根据机构Id查询课程类型
	 * @param busid
	 * @return
	 */
	public long findCountByBusId(int busid) {
	
		String hql ="select count(*) from Course where busId= :busid";
		Query query=super.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter("busid", busid);
		return (long)query.uniqueResult();
	}
	/**
	 * 
	 * @desc 通过商家id查询热门课程 分页(默认个数)
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
		Query<Course> query = super.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter(0, busId);
	    query.setMaxResults(Constants.DEFAULT_PAGESIZE);  
	    List<Course> hcl = query.list();  
	    return hcl;
	}
	/**
	 * @desc 通过商家id查询热门课程 分页(可选页面展示个数)
	 * @param busId
	 * @param pagesize
	 * @return
	 * @throws Exception
	 */
	public List<Course> courseListByHot(int busId,int pagesize) throws Exception {
		String hql = " from Course  where busId = ? and hot = 1";
		Query<Course> query = super.getSessionFactory().getCurrentSession().createQuery(hql);
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
		Query<Course> query = super.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter(0, busId);
	    List<Course> hcl = query.list();  
	    return hcl;
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
		Query query=	super.getSessionFactory().getCurrentSession().createQuery(hql);
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
		Query query=	super.getSessionFactory().getCurrentSession().createQuery(hql);
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
		Query query=	super.getSessionFactory().getCurrentSession().createQuery(hql);
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
		Query query=	super.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter(0, '%'+searchSentence+'%');
		query.setMaxResults(Constants.DEFAULT_PAGESIZE_COURSE);
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
		Query query=	super.getSessionFactory().getCurrentSession().createQuery(hql);
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
		Query query=	super.getSessionFactory().getCurrentSession().createQuery(hql);
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
	 * 按id查询并锁定相应的课程
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
	/**
	 * 根据机构Id 分页查询课程信息
	 * @param busid
	 * @return
	 * @throws Exception 
	 */
	public List<Course> findAllCourseByBusId(int busid,int pageNum) throws Exception {
		String hql =  "from Course where busId=?";
		return this.find4PageByProperty(pageNum, Constants.BUSINESSADMIN_DEFAULT_PAGESIZE, hql,new Integer[]{busid} );

	}
	public List<String> autoComplete(String key) {
		String hql = "select courseName from Course where courseName like ? ";
		
		Query query = super.getSessionFactory().getCurrentSession().createQuery(hql);
		System.out.println(key);
		query.setParameter(0, "%"+key+"%");
		return query.list();
		
	}

	
}
