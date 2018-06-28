package com.kedou.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * 商品类型联系 实体
 * @author 张天润	
 *
 */
@Entity
@Table(name="coursetyperelation")
public class CourseTypeRelation {

	@Id
	@GeneratedValue(generator="increment_generator")
	@GenericGenerator(name="increment_generator", strategy="increment")
	private int id;
	
	private int courseId;			//课程Id
	private int coursetypeId;		//课程类型Id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public int getCoursetypeId() {
		return coursetypeId;
	}
	public void setCoursetypeId(int coursetypeId) {
		this.coursetypeId = coursetypeId;
	}
	
	
}
