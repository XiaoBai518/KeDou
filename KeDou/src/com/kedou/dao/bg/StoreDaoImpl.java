package com.kedou.dao.bg;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.Business;

@Repository
public class StoreDaoImpl extends BaseDao<Business> {
	
	
	/**
	 * 按id查询并锁定相应的商家
	 * @param id
	 * @throws Exception
	 */
		public void lockBus(int id)throws Exception{
			SessionFactory sessionFactory =super.getSessionFactory();
			String hql="update Business bus set bus.state=:state where bus.busId=:id";
			Query query=sessionFactory.getCurrentSession().createQuery(hql);
			query.setParameter("state", 2);
			query.setParameter("id", id);
			query.executeUpdate();
			
		}
	
	/**
	 * 按照id来查找商家 
	 * @param id
	 * @return
	 * @throws Exception
	 * @question 是否要和 用户名和eamil查找商家 方法实现合并
	 */
	public Business findBusById(int id)throws Exception{
		SessionFactory sessionFactory=super.getSessionFactory();
		String hql="from Business as bus where bus.busId =:id";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		if(query.list().size()!=0) {
			 return (Business) query.list().get(0);
			}else {
				return null;
			}
	}
	
	/**
	 * 按照用户名和email查找商家
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public Business findBus(String account)throws Exception{
		SessionFactory sessionFactory=super.getSessionFactory();
		String hql="from Business as bus where bus.busAccount like :name or bus.busEmail like :email";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("name", "%"+account+"%");
		query.setParameter("email", "%"+account+"%");
		if(query.list().size()!=0) {
		 return (Business) query.list().get(0);
		}else {
			return null;
		}
	}
}
