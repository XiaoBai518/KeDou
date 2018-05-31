package com.kedou.dao.role;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.Role;

@Repository
public class RoleDaoImpl extends BaseDao<Role> {

	
	public Role findByRoleName(String rolename) throws Exception {
		String hql ="form Role where roleName = ?";
		String [] params = {rolename}; 
		return super.findOne(hql, params);
	}
}
