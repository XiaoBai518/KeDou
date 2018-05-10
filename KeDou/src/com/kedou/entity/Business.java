package com.kedou.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="business")
/**
 * 商家 实体
 * @author zhangtianrun
 *
 */
public class Business {
	@Id
	@GeneratedValue(generator="increment_generator")
	@GenericGenerator(name="increment_generator", strategy="increment")
	private int busId;
	private String busName;//商家名称
	private String busContact;//联系人
	private String busTel;			//联系人电话
	private String busAccount;		//账号
	private String busEmail;		//邮箱
	private String busPwd;			//密码
	private String salt; 			//二次加密中的 salt值
	private String busLicense;		//营业执照号
	private String busAddress;		//公司地址
	private String busCorporate;	//公司法人
	private Date createTime; 		//创建时间
	private Date loginTime;			//登录时间
	private String busIp; 			//用户IP
	private String lastLoginIp;     //上一次登陆IP
	private Date lastLoginTime;		//上一次登录时间
	private int loginCount;			//登录次数
	private int state=0;			//商家状态 0未激活/1已确认/2锁定
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
	public String getBusAccount() {
		return busAccount;
	}
	public void setBusAccount(String busAccount) {
		this.busAccount = busAccount;
	}
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
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public int getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}
	public Set<Announcement> getAccset() {
		return accset;
	}
	public void setAccset(Set<Announcement> accset) {
		this.accset = accset;
	}
	public String getBusIp() {
		return busIp;
	}
	public void setBusIp(String busIp) {
		this.busIp = busIp;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	
	
}
