package com.kedou.superentity;

import com.kedou.entity.Business;
import com.kedou.entity.Course;
import com.kedou.entity.Torder;
import com.kedou.entity.User;

public class SuperTorder {

	private Torder torder;
	private User user;
	private Business business;
	private Course course;
	
	public SuperTorder(Torder t,User u,Course c) {
		this.torder = t;
		this.user = u;
		this.course = c;
	}
	public SuperTorder(Torder t,Course c) {
		this.torder = t;
		this.course = c;
	}

	
	
	public Course getCourse() {
		return course;
	}


	public Torder getTorder() {
		return torder;
	}

	public User getUser() {
		return user;
	}

	public Business getBusiness() {
		return business;
	}
	
	
}
