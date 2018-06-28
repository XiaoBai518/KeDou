package com.kedou.superentity;

import java.util.List;

import com.kedou.entity.Course;

public class SuperCourse  {

	private Course course;
	private String courseType;
	private int collection; //0 代表没被收藏 1代表被收藏
	
	
	public SuperCourse(Course c,List<String> typelist) {
		this.course = c;
		
		StringBuilder coursetype = new StringBuilder();

		for(String typename:typelist) {
			coursetype.append(typename+',');
		}
		coursetype.deleteCharAt(coursetype.length()-1);
		this.courseType = coursetype.toString();
	}
	
	public SuperCourse(Course c,int collection) {
		this.course = c;
		this.collection = collection;
	}
	
	public int getCollection() {
		return collection;
	}


	public Course getCourse() {
		return course;
	}

	public String getCourseType() {
		return courseType;
	}
	
	
}
