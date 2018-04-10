package com.kedou.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.kedou.entity.Announcement;

@Repository
public class AnnouncementDao {
	@Resource
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	//添加公告
	public void insert(String accTitle,String accContent,int busId){
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		Date accPublish = new Date();    
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Announcement announcement = new Announcement();
		announcement.setAccTitle(accTitle);
		announcement.setAccContent(accContent);
		announcement.setAccPublish(df.format(accPublish));
		announcement.setAccRead(1);
		announcement.setBusId(busId);
		session.save(announcement);
		tran.commit();
		session.close();	
	}
	//查询公告
	public List<Announcement> findAll(){
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		Query query = session.createQuery("from Announcement");
		List<Announcement> list = query.list();
		tran.commit();
		session.close();
		return list;
	}
	
	//to修改公告
	public Announcement toedit(int accId){
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		Announcement announcement = session.get(Announcement.class, new Integer(accId));
		tran.commit();
		session.close();
		return announcement;
	}
	
	//真正修改公告
	public void edit(int accId,Announcement announcement){
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		Query query = session.createQuery("update Announcement set accTitle=?,accContent=?,accRead=?,accPublish=? where accId=?");
		query.setParameter(0, announcement.getAccTitle());
		query.setParameter(1, announcement.getAccContent());
		query.setParameter(2, announcement.getAccRead());
		query.setParameter(3, announcement.getAccPublish());
		query.setParameter(4, accId);
		query.executeUpdate();
		tran.commit();
	}
	//删除公告
	public void del(int accId){
		Session session = sessionFactory.openSession();
		Transaction tran =session.beginTransaction();	
		Query query = session.createQuery("delete from Announcement where accId=?");
		query.setParameter(0, accId);
		query.executeUpdate();
		tran.commit();
	}
	//根据accId查询
	public Announcement findById(int accId){
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		String hql = "from Announcement where accId=?";
		Query query = session.createQuery(hql);
		query.setParameter(0, accId);
		Announcement ann = (Announcement)query.uniqueResult();
		tran.commit();
		return ann;
	}
}
