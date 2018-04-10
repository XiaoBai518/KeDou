package com.kedou.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

	@Entity
	@Table(name="user")
	public class User {

		@Id
		@GeneratedValue(generator="increment_generator")
		@GenericGenerator(name="increment_generator", strategy="increment")
		private int userId;
		
		private String userName;//用户姓名
		private String userPwd;//用户密码
		private String userEmail;//用户邮箱
		private String userTel;//用户手机号
		private String userIdNum;//用户身份证号
		@Transient
		private Set<String> address;//用户地址
		private String userIp;
		private String userRealName;//真实姓名
		private int userGrade;//身份识别码
		@Transient
		private Set<Article> ArticleSet = new HashSet<Article>();
		public int getUserId() {
			return userId;
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
		public int getUserGrade() {
			return userGrade;
		}
		public void setUserGrade(int userGrade) {
			this.userGrade = userGrade;
		}
				
}
		
		