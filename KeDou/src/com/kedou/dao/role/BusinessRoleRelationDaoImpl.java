package com.kedou.dao.role;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.BusinessRoleRelation;

@Repository
public class BusinessRoleRelationDaoImpl extends BaseDao<BusinessRoleRelation> {

	
	
	/**
	 * 通过角色ID 查询 商家ID
	 * @param roleName
	 * @return
	 * @throws Exception 
	 */
	public int getBusIdByRoleid(int roleid) throws Exception {
		String hql = "select busId from BusinessRoleRelation  where roleId = ?";	
		Query query=super.getSessionFactory().getCurrentSession().createQuery(hql);
		
				query.setParameter(0, roleid);
				return (Integer)query.uniqueResult();

	}
	
	/**
	 * 通过商家ID 查询 角色Id
	 * @param roleName
	 * @return
	 * @throws Exception 
	 */
	public Integer getRoleidByBusid(int busid) throws Exception {
		String hql = "select roleId from BusinessRoleRelation  where busId = ?";	
		Query query=super.getSessionFactory().getCurrentSession().createQuery(hql);
		
				query.setParameter(0, busid);
				return (Integer)query.uniqueResult();
		

	}
}
