package com.kedou.superentity;

import java.util.List;

public class SuperChart {

	/**课程图表*/
	private String courseName;				//课程名字
	private List<Integer> orderNumber;		//订单数量  该List 为一周7天的订单数量 orderNumber.get(0)代表星期一的订单数量
	
	/**商家数据图表*/
	private List<Integer> orderNumberBus; //商家订单数量 
	
	
	public SuperChart(){}
	public SuperChart(String courseName, List<Integer> orderNumber) {
		this.courseName = courseName;
		this.orderNumber = orderNumber;
	}
	public SuperChart(List<Integer> orderNumberBus) {
		this.orderNumberBus = orderNumberBus;
	}
	public String getCourseName() {
		return courseName;
	}
	public List<Integer> getOrderNumber() {
		return orderNumber;
	}
	public List<Integer> getOrderNumberBus() {
		return orderNumberBus;
	}

	
	
	
	
	
	
}
