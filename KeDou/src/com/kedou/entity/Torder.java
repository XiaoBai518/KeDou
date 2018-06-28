package com.kedou.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.kedou.util.Constants;

@Entity
@Table(name="torder")
/**
 * 订单 实体
 * @author yuanyuan
 *
 */
public class Torder {
	@Id
	@GeneratedValue(generator="increment_generator")
	@GenericGenerator(name="increment_generator", strategy="increment")
	private int id;						//订单ID
	private int userId;					//用户ID
	private int busId;					//商家ID
	private int courseId;				//商品ID
	private String reserveName;			//预约人姓名
	private String reserveTel;			//预约人联系方式
	private int reserveNum;				//预约人数
	private double price;				//价格
	private int orderState;				//订单状态
	private String userNote;			//用户备注
	private Date orderCreateTime; 		//订单创建时间
	private Date orderProcessTime;		//订单接受处理时间
	public Torder() {
		//设置订单为未处理状态
		this.orderState = Constants.TORDER_STATE_UNTREATED;
	}
	
	public Date getOrderProcessTime() {
		return orderProcessTime;
	}
	public void setOrderProcessTime(Date orderProcessTime) {
		this.orderProcessTime = orderProcessTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getBusId() {
		return busId;
	}
	public void setBusId(int busId) {
		this.busId = busId;
	}
	public int getReserveNum() {
		return reserveNum;
	}
	public void setReserveNum(int reserveNum) {
		this.reserveNum = reserveNum;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getOederState() {
		return orderState;
	}
	public void setOederState(int orderState) {
		this.orderState = orderState;
	}
	public String getUserNote() {
		return userNote;
	}
	public void setUserNote(String userNote) {
		this.userNote = userNote;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public int getOrderState() {
		return orderState;
	}
	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}
	public Date getOrderCreateTime() {
		return orderCreateTime;
	}
	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}
	public String getReserveName() {
		return reserveName;
	}
	public void setReserveName(String reserveName) {
		this.reserveName = reserveName;
	}
	public String getReserveTel() {
		return reserveTel;
	}
	public void setReserveTel(String reserveTel) {
		this.reserveTel = reserveTel;
	}
	
}

