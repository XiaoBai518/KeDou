package com.kedou.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="collection")
/**
 * 课程 实体
 * @author zhangtainrun
 *
 */
public class Course {

	@Id
	@GeneratedValue(generator="increment_generator")
	@GenericGenerator(name="increment_generator", strategy="increment")
	private int courseId;
	private String courseName;//课程名字
	private String description;//课程描述
	private int courseType;//课程类型
	private Date courseStartTime;//课程开始时间
	private Date couseEndTime;//课程结束时间
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
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCourseType() {
		return courseType;
	}
	public void setCourseType(int courseType) {
		this.courseType = courseType;
	}
	
	public Date getCourseStartTime() {
		return courseStartTime;
	}
	public void setCourseStartTime(Date courseStartTime) {
		this.courseStartTime = courseStartTime;
	}
	public Date getCouseEndTime() {
		return couseEndTime;
	}
	public void setCouseEndTime(Date couseEndTime) {
		this.couseEndTime = couseEndTime;
	}
	public double getCoursePrice() {
		return coursePrice;
	}
	public void setCoursePrice(double coursePrice) {
		this.coursePrice = coursePrice;
	}
	
	
	
	
	
}
