package com.kedou.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="announcement")
public class Announcement {
	private int accId;				//公告ID
	private String accPublish;		//公告发布时间
	private int busId;				//公告发布人
	private String accTitle;		//公告标题
	private String accContent;		//公告内容
	private int accRead;			//已读人数
	@Id
	@GeneratedValue(generator="my_gen")
	@GenericGenerator(name="my_gen",strategy="increment")
	public int getAccId() {
		return accId;
	}
	public void setAccId(int accId) {
		this.accId = accId;
	}
	public String getAccPublish() {
		return accPublish;
	}
	public void setAccPublish(String accPublish) {
		this.accPublish = accPublish;
	}
	
	public String getAccTitle() {
		return accTitle;
	}
	public void setAccTitle(String accTitle) {
		this.accTitle = accTitle;
	}
	public String getAccContent() {
		return accContent;
	}
	public void setAccContent(String accContent) {
		this.accContent = accContent;
	}
	public int getAccRead() {
		return accRead;
	}
	public void setAccRead(int accRead) {
		this.accRead = accRead;
	}
	public int getBusId() {
		return busId;
	}
	public void setBusId(int busId) {
		this.busId = busId;
	}
	
}
