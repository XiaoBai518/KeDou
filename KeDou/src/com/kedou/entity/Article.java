package com.kedou.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 文章
 * @author 张小白
 *
 */
@Entity
@Table(name="article")
public class Article implements java.io.Serializable{
	@Id
	@GeneratedValue(generator="increment_generator")
	@GenericGenerator(name="increment_generator", strategy="increment")
	private int id;		
	private String titleimg;	//文章主题图片地址
	private String img;			//文章图片地址
	
	private String title;		//文章主题
	private String author;		//文章作者
	private String content; 	//文章内容
	
	private int likes;			//点赞人数
	private int views;			//文章点击量
	private Date dates;			//文章发表时间
	private String hotspots;	//文章热点标签id
	private int texttype;		//文章类型id
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getTitleimg() {
		return titleimg;
	}
	public void setTitleimg(String titleimg) {
		this.titleimg = titleimg;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public Date getDates() {
		return dates;
	}
	public void setDates(Date dates) {
		this.dates = dates;
	}
	public String getHotspots() {
		return hotspots;
	}
	public void setHotspots(String hotspots) {
		this.hotspots = hotspots;
	}
	public int getTexttype() {
		return texttype;
	}
	public void setTexttype(int texttype) {
		this.texttype = texttype;
	}
}
