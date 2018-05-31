package com.kedou.dao.role;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.UserRoleRelation;

@Repository
public class UserRoleRelationDaoImpl extends BaseDao<UserRoleRelation> {

	
	/**
	 * 通过角色ID 查询 用户ID
	 * @param roleName
	 * @return
	 * @throws Exception 
	 */
	public int getUseridByRoleid(int roleid) throws Exception {
		String hql = "select userId from UserRoleRelation  where roleId = ?";	
		Query query=super.getSessionFactory().getCurrentSession().createQuery(hql);
		
				query.setParameter(0, roleid);
				return (Integer)query.uniqueResult();

	}
	
	/**
	 * 通过用户ID 查询 角色Id
	 * @param roleName
	 * @return
	 * @throws Exception 
	 */
	public Integer getRoleidByUserid(int userid) throws Exception {
		String hql = "select roleId from UserRoleRelation  where userId = ?";	
		Query query=super.getSessionFactory().getCurrentSession().createQuery(hql);
		
				query.setParameter(0, userid);
				return (Integer)query.uniqueResult();
		

	}

}
