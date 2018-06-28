package com.kedou.dao.torder;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.Torder;

@Repository
public class TorderDaoImpl extends BaseDao<Torder> {

	
	/**
	 * 商家处理订单
	 * @param params
	 * @throws Exception
	 */
	public void updateState(Object [] params) throws Exception {
		String hql = "update Torder T  set T.orderState= ?, T.orderProcessTime = ? where T.id= ?";
		this.updateByProperty(hql, params);
	}
	/**
	    * 
		* @desc 通过商家ID查询预约订单 (分页)
		* @author 张天润
		* @param orderState 订单状态
		* @param busid
		* @param pageSize
		* @param pageNum
		* @createDate
		* @return User
		* @throws Exception
		*/
	public List<Torder> findOrderByUserId(int busid,int pageSize,int pageNum,int orderState) throws Exception{
		String hql ="from Torder where busId =? and orderState = ?";
			return super.find4PageByProperty(pageNum, pageSize, hql, new Integer [] {busid,orderState});
	}
	/**
	    * 
		* @desc 根据商家id查询 预约订单的总数
		* @author 张天润
		* @param busid
		* @param orderState
		* @createDate
		* @return User
		* @throws Exception
		*/
	public long findOrderCountByBusId(int busid,int orderState) throws Exception {
		String hql ="select count(*) from Torder where busId= :busid and orderState = :orderState";
		Query query=super.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter("busid", busid);
		query.setParameter("orderState", orderState);
		return (long)query.uniqueResult();
	}
	/**
	    * 
		* @desc 根据商家id 和 日期 查询其预约订单的总数
		* @author 张天润
		* @param busid
		* @param orderState
		* @createDate
		* @return User
		* @throws Exception
		*/
	public long findOrderCountByDate(int busid,int orderState,Date date) throws Exception {
		String hql ="select count(*) from Torder where busId= :busid and orderState = :orderState and Date(orderProcessTime) = :Date";
		Query query=super.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter("busid", busid);
		query.setParameter("orderState", orderState);
		query.setDate("Date", date);
		return (long)query.uniqueResult();
	}
	/**
	    * 
		* @desc 根据商品idList 和 日期 查询其预约订单的总数
		* @author 张天润
		* @param busid
		* @param orderState
		* @createDate
		* @return User
		* @throws Exception
		*/
	public List<Object []>findOrderCountByDateCourseIdList(List<Integer> courseidList,int orderState,Date date) throws Exception {
		String hql ="select courseId, count(*) from Torder where courseId in (:courseidList) and orderState = :orderState and Date(orderProcessTime) = :date group by courseId";
		Query query=super.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter("courseidList", courseidList);
		query.setParameter("orderState", orderState);
		query.setDate("date", date);
		
		return query.list();	
	}
	/**
	    * 
		* @desc 根据商家Id 和 日期 查询其全部订单（忽略 处理和未处理状态）的总数
		* @author 张天润
		* @param busid
		* @param orderState
		* @createDate
		* @return User
		* @throws Exception
		*/
	public long findOrderCountByDateBusId(int busid,Date date) throws Exception {
		String hql ="select count(*) from Torder where busId  = :busid  and Date(orderProcessTime) = :date ";
		Query query=super.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter("busid", busid);
		query.setDate("date", date);
		
		return (long)query.uniqueResult();
	}
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
