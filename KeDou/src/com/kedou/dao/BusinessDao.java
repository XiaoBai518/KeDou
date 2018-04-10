package com.kedou.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.kedou.entity.Business;

@Repository
public class BusinessDao {
	@Resource
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	//商家注册
	public Boolean insert(String busAccount){		
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		String hql = "from Business where busAccount=?";
		Query<Business> query = session.createQuery(hql);
		query.setParameter(0, busAccount);
		List<Business> list = query.list();
		if(list.size()==0){
			tran.commit();
			return true;
		}else{
			return false;
		}	
	}
	//添加商家个人信息
	public Boolean insert1(String busEmail){
		Session session = sessionFactory.openSession();
		Transaction tran = session .beginTransaction();
		String hql = "from Business where busEmail=?";
		Query<Business> query = session.createQuery(hql);
		query.setParameter(0, busEmail);
		List<Business> list = query.list();
		if(list.size()==0){
			tran.commit();
			return true;
		}else{
			return false;
		}
	}
	//添加商家信息
	public void insert2(String busName,String busLicense,String busAddress,String busCorporate,
							HttpSession session,HttpServletResponse response){
		Business business = new Business();
		business.setBusAccount((String)session.getAttribute("busAccount"));
		business.setBusPwd((String)session.getAttribute("busPwd"));
		business.setBusContact((String)session.getAttribute("busContact"));
		business.setBusTel((String)session.getAttribute("busTel"));
		business.setBusEmail((String)session.getAttribute("busEmail"));
		business.setBusName(busName);
		business.setBusAddress(busAddress);
		business.setBusLicense(busLicense);
		business.setBusCorporate(busCorporate);
		business.setBusState("在线中");
		Session session1 = sessionFactory.openSession();
		Transaction tran = session1.beginTransaction();
		session1.save(business);
		tran.commit();
		session1.close();
		response.setContentType("text/html;charset=utf-8");
		
	}
	//商家登录
	public Boolean get(String busAccount,String busPwd,HttpSession session1){
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		String hql = "from Business where busAccount=?";
		Query<Business> query = session.createQuery(hql);
		query.setParameter(0, busAccount);
		List<Business> list = query.list();
		if(list.size()==1&&list.get(0).getBusPwd().equals(busPwd)){	
			tran.commit();
			session1.setAttribute("business", list.get(0));
			return true;
		}else{
			return false;
		}
	}
	//更新商家状态
	public void update(int busId){
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		Business bus = session.get(Business.class, new Integer(busId));
		if(bus.getBusState().equals("在线中")){
			bus.setBusState("离线");
			session.update(bus);
			tran.commit();
			session.close();
		}else{
			bus.setBusState("在线中");
			session.update(bus);
			tran.commit();
			session.close();
		}
	}
	//根据商家ID查询
	public Business getById(int busId){
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		Business bus = session.get(Business.class, new Integer(busId));
		tran.commit();
		return bus;
	}
	//查询所有用户
	public List<Business> findAll(){
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		String hql = "from Business";
		Query<Business> query = session.createQuery(hql);
		List<Business> list = query.list();
		return list;
	}
}
