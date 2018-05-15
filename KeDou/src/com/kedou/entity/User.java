package com.kedou.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="user")
/**
 * 用户 实体
 * @author zhangtianrun
 *
 */
public class User {

		@Id
		@GeneratedValue(generator="increment_generator")
		@GenericGenerator(name="increment_generator", strategy="increment")
		private int userId;
		
		private String userName="null";		//用户姓名
		private String userPwd;				//用户密码
		private String userAcount;          //用户账号（管理员登录用）
		private String salt; 				//二次加密中的 salt值
		private String userEmail;			//用户邮箱
		private String gender;				//用户性别
		private String userTel;				//用户手机号
		private String userIdNum;			//用户身份证号
		@Transient
		private Set<String> address;		//用户地址
		private String userIp; 				//用户IP
		private String userRealName;		//真实姓名
		private String userDiscription;		//个人描述
		private int state=0;				//状态 0为未激活状态，1为正常状态，2为锁定状态
		private String verifyNum;			//激活验证码
		private Date createTime; 			//创建时间
		private Date loginTime;				//登录时间
		private Date lastLoginTime;			//上一次登录时间
		private String lastLoginIp;	        //上一次登陆IP
		private int loginCount;				//登录次数
		private String userIcon;			//用户头像
		
		public User (String username,String pwd) {
			if(username.contains("@")){
				//包含@为邮箱
				this.userEmail = username;
			}else {
				//不包含@为手机
				this.userTel = username;		
			}	
			this.userPwd = pwd;
		}
		public User() {
			
		}
		
		
		public String getUserIcon() {
			return userIcon;
		}
		public void setUserIcon(String userIcon) {
			this.userIcon = userIcon;
		}		
		public int getUserId() {
			return userId;
		}
		public String getUserDiscription() {
			return userDiscription;
		}


		public void setUserDiscription(String userDiscription) {
			this.userDiscription = userDiscription;
		}


		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getUserPwd() {
			return userPwd;
		}
		public void setUserPwd(String userPwd) {
			this.userPwd = userPwd;
		}
		public String getUserEmail() {
			return userEmail;
		}
		public void setUserEmail(String userEmail) {
			this.userEmail = userEmail;
		}
		public String getUserTel() {
			return userTel;
		}
		public void setUserTel(String userTel) {
			this.userTel = userTel;
		}
		public String getUserIdNum() {
			return userIdNum;
		}
		public void setUserIdNum(String userIdNum) {
			this.userIdNum = userIdNum;
		}
		public Set<String> getAddress() {
			return address;
		}
		public void setAddress(Set<String> address) {
			this.address = address;
		}
		public String getUserIp() {
			return userIp;
		}
		public void setUserIp(String userIp) {
			this.userIp = userIp;
		}
		public String getUserRealName() {
			return userRealName;
		}
		public void setUserRealName(String userRealName) {
			this.userRealName = userRealName;
		}
		public String getVerifyNum() {
			return verifyNum;
		}
		public void setVerifyNum(String verifyNum) {
			this.verifyNum = verifyNum;
		}
		public String getSalt() {
			return salt;
		}
		public void setSalt(String salt) {
			this.salt = salt;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public Date getLastLoginTime() {
			return lastLoginTime;
		}
		public void setLastLoginTime(Date lastLoginTime) {
			this.lastLoginTime = lastLoginTime;
		}
		public int getLoginCount() {
			return loginCount;
		}
		public void setLoginCount(int loginCount) {
			this.loginCount = loginCount;
		}
		public Date getLoginTime() {
			return loginTime;
		}
		public void setLoginTime(Date loginTime) {
			this.loginTime = loginTime;
		}
		public String getLastLoginIp() {
			return lastLoginIp;
		}
		public void setLastLoginIp(String lastLoginIp) {
			this.lastLoginIp = lastLoginIp;
		}
		public String getUserAcount() {
			return userAcount;
		}
		public void setUserAcount(String userAcount) {
			this.userAcount = userAcount;
		}
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		
		
}
		
		