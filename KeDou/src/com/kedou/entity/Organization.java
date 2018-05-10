package com.kedou.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="organization")
public class Organization {
	@Id
	@GeneratedValue(generator="increment_generator")
	@GenericGenerator(name="increment_generator", strategy="increment")
	private int id;
	private String busName;//商家名称
	private String busContact;//联系人
    private String busTel;//电话
    private String busLicense;//营业执照号
    private String busAddress;//公司地址
    private String busCorporate;//公司法人
    private int accId;//公告id
    private int busState;//机构状态 1代表申请状态2代表允许入驻3代表锁定状态
    private String busAccount;//商家账号
    private String busEmial;//邮箱
    private String busPwd;//密码
    
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBusName() {
		return busName;
	}
	public void setBusName(String busName) {
		this.busName = busName;
	}
	public String getBusContact() {
		return busContact;
	}
	public int getBusState() {
		return busState;
	}
	public void setBusState(int busState) {
		this.busState = busState;
	}
	public void setBusContact(String busContact) {
		this.busContact = busContact;
	}
	public String getBusTel() {
		return busTel;
	}
	public void setBusTel(String busTel) {
		this.busTel = busTel;
	}
	public String getBusLicense() {
		return busLicense;
	}
	public void setBusLicense(String busLicense) {
		this.busLicense = busLicense;
	}
	public String getBusAddress() {
		return busAddress;
	}
	public void setBusAddress(String busAddress) {
		this.busAddress = busAddress;
	}
	public String getBusCorporate() {
		return busCorporate;
	}
	public void setBusCorporate(String busCorporate) {
		this.busCorporate = busCorporate;
	}
	public int getAccId() {
		return accId;
	}
	public void setAccId(int accId) {
		this.accId = accId;
	}
	public String getBusAccount() {
		return busAccount;
	}
	public void setBusAccount(String busAccount) {
		this.busAccount = busAccount;
	}
	public String getBusEmial() {
		return busEmial;
	}
	public void setBusEmial(String busEmial) {
		this.busEmial = busEmial;
	}
	public String getBusPwd() {
		return busPwd;
	}
	public void setBusPwd(String busPwd) {
		this.busPwd = busPwd;
	}
    
    
}
