package com.kedou.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author 张小白
 *
 */
@Entity
@Table(name="adminrolerelation")
public class AdminRoleRelation {
	
	@Id
	@GeneratedValue(generator="increment_generator")
	@GenericGenerator(name="increment_generator", strategy="increment")
	private int arrId;
	
	private int adminId;//管理员ID
	private int roleId;//角色ID
	public int getArrId() {
		return arrId;
	}
	public void setArrId(int arrId) {
		this.arrId = arrId;
	}
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	
	
}
