package com.kedou.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="admin")
/**
 * 管理员实体
 * @author 宋亚楼
 * */
public class Admin {
	@Id
	@GeneratedValue(generator="increment_generator")
	@GenericGenerator(name="increment_generator", strategy="increment")
	private int adminId;				//管理员Id
	private String adminAccount;		//管理员账户
	private String adminPwd;			//管理员密码
	private String adminSalt;			//salt值
	private int state;				//状态 1为正常状态，2为锁定状态
	private Date createTime; 			//创建时间
	private Date loginTime;				//登录时间
	private Date lastLoginTime;			//上一次登录时间
	private String lastLoginIp;	        //上一次登陆IP
	private String loginIp;				//登录Ip
	private int loginCount;				//登录次数
	private String adminImage;			//管理员头像
	
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public String getAdminAccount() {
		return adminAccount;
	}
	public void setAdminAccount(String adminAccount) {
		this.adminAccount = adminAccount;
	}
	
	public String getAdminPwd() {
		return adminPwd;
	}
	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}
	public String getAdminSalt() {
		return adminSalt;
	}
	public void setAdminSalt(String adminSalt) {
		this.adminSalt = adminSalt;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public int getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}
	public String getAdminImage() {
		return adminImage;
	}
	public void setAdminImage(String adminImage) {
		this.adminImage = adminImage;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
	
}
