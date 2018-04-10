package com.kedou.service;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.kedou.dao.BusinessDao;
import com.kedou.entity.Business;

@Service
public class BusinessService {
	@Resource
	private BusinessDao businessDao;

	public BusinessDao getBusinessDao() {
		return businessDao;
	}

	public void setBusinessDao(BusinessDao businessDao) {
		this.businessDao = businessDao;
	}
	
	//商家注册
	public Boolean add(String busAccount){
		return businessDao.insert(busAccount);
	}
	//商家个人信息
	public Boolean add1(String busEmail){
		return businessDao.insert1(busEmail);
	}
	//添加商家信息
	public void add2(String busName,String busLicense,String busAddress,String busCorporate,
					HttpSession session,HttpServletResponse response){
		businessDao.insert2(busName, busLicense, busAddress, busCorporate, session,response);
	}
	//商家登录
	public Boolean check(String busAccount,String busPwd,HttpSession session){
		return businessDao.get(busAccount, busPwd,session);
	}
	//更新商家状态
	public void update(int busId){
		businessDao.update(busId);
	}
	//根据商家ID查询
	public Business getById(int busId){
		return businessDao.getById(busId);
	}
	//查询所有用户
	public List<Business> findAll(){
		return businessDao.findAll();
	}
}
