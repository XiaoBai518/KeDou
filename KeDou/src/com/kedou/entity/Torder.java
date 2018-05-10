package com.kedou.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
	private int id;//订单ID
	private String userName;//用户姓名
	private String userTel;//用户电话
	private int catId;//商品编号
	private String catName;//商品名称
	private int busId;
	private String busName;
	private int catNum;//预约人数
	private int catPrice;//价格
	private int orderState;//订单状态
	private String userNote;//用户备注
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserTel() {
		return userTel;
	}
	public int getBusId() {
		return busId;
	}
	public void setBusId(int busId) {
		this.busId = busId;
	}
	public String getBusName() {
		return busName;
	}
	public void setBusName(String busName) {
		this.busName = busName;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public int getCatId() {
		return catId;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public int getCatNum() {
		return catNum;
	}
	public void setCatNum(int catNum) {
		this.catNum = catNum;
	}
	public int getCatPrice() {
		return catPrice;
	}
	public void setCatPrice(int catPrice) {
		this.catPrice = catPrice;
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
	
	
}

