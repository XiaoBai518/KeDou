/**
 * 
 */
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
@Table(name="userrolerelation")
public class UserRoleRelation {

	@Id
	@GeneratedValue(generator="increment_generator")
	@GenericGenerator(name="increment_generator", strategy="increment")
	private int urrId;
	
	private int userId;//用户ID
	private int roleId;//角色ID
	public int getUrrId() {
		return urrId;
	}
	public void setUrrId(int urrId) {
		this.urrId = urrId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	
}
