package com.kedou.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="address")
/**
 * 地址 实体
 * @author zhangtianrun
 *
 */
public abstract class Address {
	@Id
	@GeneratedValue(generator="increment_generator")
	@GenericGenerator(name="increment_generator", strategy="increment")
	private int addId;
	private int userId;
	private String userName;
	private String userTel;
	private String address;
}
