package com.kedou.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="coursetype")
/**
 * 商品类型 实体
 * @author 张天润	
 *
 */
public class CourseType {

	@Id
	@GeneratedValue(generator="increment_generator")
	@GenericGenerator(name="increment_generator", strategy="increment")
	private int id;
	
	private String typeName;        
	private Date typeCreateTime;	//类型创建时间
	private int typeCreator;		//类型创建者
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Date getTypeCreateTime() {
		return typeCreateTime;
	}
	public void setTypeCreateTime(Date typeCreateTime) {
		this.typeCreateTime = typeCreateTime;
	}
	public int getTypeCreator() {
		return typeCreator;
	}
	public void setTypeCreator(int typeCreator) {
		this.typeCreator = typeCreator;
	}
	

	
	
}
