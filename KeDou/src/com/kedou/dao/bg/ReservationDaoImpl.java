package com.kedou.dao.bg;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.Torder;
@Repository
public class ReservationDaoImpl extends BaseDao<Torder> {
	/**
	 * 按id查询并锁定相应的订单
	 * @param id
	 * @throws Exception
	 */
		public void lockRe(int id)throws Exception{
			SessionFactory sessionFactory =super.getSessionFactory();
			String hql="update Torder torder set torder.orderState=:state where torder.id=:id";
			Query query=sessionFactory.getCurrentSession().createQuery(hql);
			query.setParameter("state", 2);
			query.setParameter("id", id);
			query.executeUpdate();
			
		}
}
