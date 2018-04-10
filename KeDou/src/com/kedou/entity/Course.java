package com.kedou.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="course")
public class Course {

	@Id
	@GeneratedValue(generator="increment_generator")
	@GenericGenerator(name="increment_generator", strategy="increment")
	private int courseId;
	private String courseName;//课程名字
	private String courseDis;//课程描述
	private int courseType;//课程类型
	private String courseStartTime;//课程开始时间
	private String couseEndTime;//课程结束时间
	private double coursePrice;//课程价格
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
	public String getCourseDis() {
		return courseDis;
	}
	public void setCourseDis(String courseDis) {
		this.courseDis = courseDis;
	}
	public int getCourseType() {
		return courseType;
	}
	public void setCourseType(int courseType) {
		this.courseType = courseType;
	}
	public String getCourseStartTime() {
		return courseStartTime;
	}
	public void setCourseStartTime(String courseStartTime) {
		this.courseStartTime = courseStartTime;
	}
	public String getCouseEndTime() {
		return couseEndTime;
	}
	public void setCouseEndTime(String couseEndTime) {
		this.couseEndTime = couseEndTime;
	}
	public double getCoursePrice() {
		return coursePrice;
	}
	public void setCoursePrice(double coursePrice) {
		this.coursePrice = coursePrice;
	}
	
	
	
	
	
}
