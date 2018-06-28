package com.kedou.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="businessvisitnumber")
/**
 * 商家店铺访问量类
 * @author 张小白
 *
 */
public class BusinessVisitNumber {

	@Id
	@GeneratedValue(generator="increment_generator")
	@GenericGenerator(name="increment_generator", strategy="increment")
	private int id;
	
	private int busId;
	private int visitNumber;
	private Date date;
	
	public BusinessVisitNumber() {}
	public BusinessVisitNumber(int busid) {
		this.busId = busid;
		this.visitNumber = 0;
		this.date = new Date();
	}
	
	public int getBusId() {
		return busId;
	}
	public void setBusId(int busId) {
		this.busId = busId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVisitNumber() {
		return visitNumber;
	}
	public void setVisitNumber(int visitNumber) {
		this.visitNumber = visitNumber;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
