package com.kedou.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="coursetyperelation")
/**
 * 商品类型联系 实体
 * @author 张天润	
 *
 */
public class CourseTypeRelation {

	@Id
	@GeneratedValue(generator="increment_generator")
	@GenericGenerator(name="increment_generator", strategy="increment")
	private int id;
	
	private int courseId;        //课程ID
	private int buscourtypeId;   //商家课程类型联系Id            
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
	public int getBuscourtypeId() {
		return buscourtypeId;
	}
	public void setBuscourtypeId(int buscourtypeId) {
		this.buscourtypeId = buscourtypeId;
	}
}
