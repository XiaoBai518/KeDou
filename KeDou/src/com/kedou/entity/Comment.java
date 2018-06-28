package com.kedou.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="comment")
/**
 * 评价 实体
 * @author zhangtianrun
 *
 */
public class Comment {
	@Id
	@GeneratedValue(generator="increment_generator")
	@GenericGenerator(name="increment_generator", strategy="increment")
	private int comId;
	
	private int userId; 	//用户Id
	private String userName;//用户名称
	private int articleId;//文章ID
	private String comments;//评价
	private String comimg1;//附图1
	private String comimg2;//附图2
	private String comimg3;//附图3
	private String userIcon;//用户头像
	private Date publicTime;//发布时间
	public int getComId() {
		return comId;
	}
	public void setComId(int comId) {
		this.comId = comId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getComimg1() {
		return comimg1;
	}
	public void setComimg1(String comimg1) {
		this.comimg1 = comimg1;
	}
	public String getComimg2() {
		return comimg2;
	}
	public void setComimg2(String comimg2) {
		this.comimg2 = comimg2;
	}
	public String getComimg3() {
		return comimg3;
	}
	public void setComimg3(String comimg3) {
		this.comimg3 = comimg3;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
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
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getPublicTime() {
		return publicTime;
	}
	public void setPublicTime(Date publicTime) {
		this.publicTime = publicTime;
	}
	
	
	
}
