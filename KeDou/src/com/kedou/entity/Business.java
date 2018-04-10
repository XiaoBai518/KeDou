package com.kedou.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="business")
public class Business {
	
	@Id
	@GeneratedValue(generator="my_gen")
	@GenericGenerator(name="my_gen",strategy="increment")
	private int busId;				//商家ID
	private String busName;			//商家名称
	private String busContact;		//联系人
	private String busTel;			//联系人电话
	private String busAccount;		//账号
	private String busEmail;		//邮箱
	private String busPwd;			//密码
	private String busLicense;		//营业执照号
	private String busAddress;		//公司地址
	private String busCorporate;	//公司法人
	private String busState;		//商家状态
	@Transient
	private Set<Announcement> accset = new HashSet<Announcement>();
		
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
	public String getBusContact() {
		return busContact;
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
	@Column(name="BUSACCOUNT",unique=true)
	public String getBusAccount() {
		return busAccount;
	}
	public void setBusAccount(String busAccount) {
		this.busAccount = busAccount;
	}
	@Column(name="BUSEMAIL",unique=true)
	public String getBusEmail() {
		return busEmail;
	}
	public void setBusEmail(String busEmail) {
		this.busEmail = busEmail;
	}
	
	public String getBusPwd() {
		return busPwd;
	}
	public void setBusPwd(String busPwd) {
		this.busPwd = busPwd;
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
	
	public Set<Announcement> getAccset() {
		return accset;
	}
	public void setAccset(Set<Announcement> accset) {
		this.accset = accset;
	}
	public String getBusState() {
		return busState;
	}
	public void setBusState(String busState) {
		this.busState = busState;
	}	
	
	
}
