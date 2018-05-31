package com.kedou.dao.role;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.AdminRoleRelation;

@Repository
public class AdminRoleRelationDaoImpl extends BaseDao<AdminRoleRelation> {

	
	/**
	 * 通过角色ID 查询 用户ID
	 * @param roleName
	 * @return
	 * @throws Exception 
	 */
	public int getAdminidByRoleid(int roleid) throws Exception {
		String hql = "select adminId from AdminRoleRelation  where roleId = ?";	
		Query query=super.getSessionFactory().getCurrentSession().createQuery(hql);
		
				query.setParameter(0, roleid);
				return (Integer)query.uniqueResult();

	}
	
	/**
	 * 通过管理员ID 查询 角色Id
	 * @param roleName
	 * @return
	 * @throws Exception 
	 */
	public Integer getRoleidByAdminid(int adminid) throws Exception {
		String hql = "select roleId from AdminRoleRelation  where adminId = ?";	
		Query query=super.getSessionFactory().getCurrentSession().createQuery(hql);
		
				query.setParameter(0, adminid);
				return (Integer)query.uniqueResult();
	}

}
