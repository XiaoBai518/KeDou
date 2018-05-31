package com.kedou.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="address")
/**
 * 地址 实体
 * @author zhangtianrun
 *
 */
public class Address {
	@Id
	@GeneratedValue(generator="increment_generator")
	@GenericGenerator(name="increment_generator", strategy="increment")
	private int Id;
	private int userId;
	private String city;
	private String area;
	@Transient
	private String address; 		//不是数据库字段
	
	public Address() {}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress() {
		if(!"".equals(this.city)&&!"".equals(this.area)) {
			this.address = this.city+this.area;
		}else {
			this.address = null;
		}
	
		
	}
	
	
}
