package com.kedou.dao.business.notice;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.BusinessNotice;

@Repository
public class NoticeDaoImpl extends BaseDao<BusinessNotice>{

	
	public List<BusinessNotice> findNoticeByBusid(int busid) {
		
		String hql = "from BusinessNotice as bn where bn.busId = :busid";
		Query query = super.getSessionFactory().getCurrentSession().createQuery(hql);
		
		query.setParameter("busid", busid);
		return query.list();
	}
}
