package com.kedou.dao.torder;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.Torder;

@Repository
public class TorderDaoImpl extends BaseDao<Torder> {

	 /**
	 * 
	 * @desc 根据关键字查找预约
	 * @author yuanyuan
	 * @createDate 2018年3月29日
	 * @return List<Torder>
	 * @throws Exception
	 */
	public List<Torder> findByCheck(String check){
		String hpl = "from Torder where userName like ? or busName like ? or catName like ? ";
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hpl);
		query.setParameter(0, "%"+check+"%");
		query.setParameter(1, "%"+check+"%");
		query.setParameter(2, "%"+check+"%");
		List<Torder> ocl = query.list();
		return ocl;
	}
	/**
	    * 
		* @desc 通过用户ID查询预约课程
		* @author 陈
		* @createDate
		* @return User
		* @throws Exception
		*/
	public List<Torder> findByUserId(int userid) throws Exception{
		SessionFactory sessionFactory = super.getSessionFactory();
		String hql ="from Torder where userId = :id";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", userid);
		return query.list();
	}
	/**
	    * 
		* @desc 通过课程ID和用户Id删除预约课程
		* @author 陈
		* @createDate
		* @return User
		* @throws Exception
		*/
	public void deleteByCourseId(int courseid, int userid) throws Exception{
		SessionFactory sessionFactory = super.getSessionFactory();
		String hql ="delete Torder  where courseId = :id and userId = :userid";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", courseid);
		query.setParameter("userid", userid);
		query.executeUpdate();
	}
	/**
	    * 
		* @desc 预约课程分页查询
		* @author 陈
		* @createDate
		* @return User
		* @throws Exception
		*/
	public List<Torder> findByPage(int pageNum, int pageSize, Object[] params) throws Exception {
		String hql ="from Torder where userId= ?";
		return super.find4PageByProperty(pageNum, pageSize, hql, params);
		
	}
	/**
	    * 
		* @desc 根据用户id查询其预约课程的总数
		* @author 陈
		* @createDate
		* @return User
		* @throws Exception
		*/
	public long findCountByUserId(int userid) throws Exception {
		SessionFactory sessionFactory = super.getSessionFactory();
		String hql ="select count(*) from Torder where userId= :userid";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userid", userid);
		return (long)query.uniqueResult();
	}
	
}
