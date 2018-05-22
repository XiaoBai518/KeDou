package com.kedou.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="course")
/**
 * 课程 实体
 * @author 张天润
 *
 */
public class Course {

	@Id
	@GeneratedValue(generator="increment_generator")
	@GenericGenerator(name="increment_generator", strategy="increment")
	private int courseId;      		//课程ID
	private int busId;        		//机构ID
	private String courseName;		//课程名字
	private String description;		//课程描述
	private Date courseStartTime;	//课程开始时间
	private Date courseEndTime;		//课程结束时间
	private double coursePrice;		//课程价格
	private int courseSold;			//课程已售数量
	private String courseImg;		//课程图片
	private int hot;				//是否热门
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
	public Date getCourseStartTime() {
		return courseStartTime;
	}
	public void setCourseStartTime(Date courseStartTime) {
		this.courseStartTime = courseStartTime;
	}

	public Date getCourseEndTime() {
		return courseEndTime;
	}
	public void setCourseEndTime(Date courseEndTime) {
		this.courseEndTime = courseEndTime;
	}
	public double getCoursePrice() {
		return coursePrice;
	}
	public void setCoursePrice(double coursePrice) {
		this.coursePrice = coursePrice;
	}
	public int getBusId() {
		return busId;
	}
	public void setBusId(int busId) {
		this.busId = busId;
	}
	public int getCourseSold() {
		return courseSold;
	}
	public void setCourseSold(int courseSold) {
		this.courseSold = courseSold;
	}
	public String getCourseImg() {
		return courseImg;
	}
	public void setCourseImg(String courseImg) {
		this.courseImg = courseImg;
	}
	public int getHot() {
		return hot;
	}
	public void setHot(int hot) {
		this.hot = hot;
	}
	
	
	
	
	
}
