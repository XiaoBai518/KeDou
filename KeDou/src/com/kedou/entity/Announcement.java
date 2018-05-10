package com.kedou.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="announcement")
/**
 * 公告 实体
 * @author zhangtianrun
 *
 */
public class Announcement {
	@Id
	@GeneratedValue(generator="increment_generator")
	@GenericGenerator(name="increment_generator", strategy="increment")
	private int annid;
	private Date annPublish;//公告发布时间
	private int busId;//公告发布人
	private String annTitle;//公告标题
	private String annContent;//公告内容
	private String  annRead;//已读人数
	public int getAnnid() {
		return annid;
	}
	public void setAnnid(int annid) {
		this.annid = annid;
	}
	public Date getAnnPublish() {
		return annPublish;
	}
	public void setAnnPublish(Date annPublish) {
		this.annPublish = annPublish;
	}
	public int getBusId() {
		return busId;
	}
	public void setBusId(int busId) {
		this.busId = busId;
	}
	public String getAnnTitle() {
		return annTitle;
	}
	public void setAnnTitle(String annTitle) {
		this.annTitle = annTitle;
	}
	public String getAnnContent() {
		return annContent;
	}
	public void setAnnContent(String annContent) {
		this.annContent = annContent;
	}
	public String getAnnRead() {
		return annRead;
	}
	public void setAnnRead(String annRead) {
		this.annRead = annRead;
	}
	
	
}
