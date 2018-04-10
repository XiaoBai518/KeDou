package com.kedou.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.kedou.entity.Article;
import com.kedou.entity.User;

@Repository
public class ArticleDao {
	@Resource
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	//添加推荐文章
	public void insert(Article article,int userId){
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		session.save(article);
		tran.commit();
		session.close();
	}
	//to修改文章
	public Article toedit(int artId) {
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		Article article = session.get(Article.class, new Integer(artId));
		tran.commit();
		session.close();
		return article;
	}
	//真正修改
	public void edit(int artId,Article article){
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		Query query = session.createQuery("update Article set title=?,content=?,publish=?,userId=? where artId=?");
		query.setParameter(0, article.getTitle());
		query.setParameter(1, article.getContent());
		query.setParameter(2, article.getPublish());
		query.setParameter(3, article.getUserId());
		query.setParameter(4,artId);
		query.executeUpdate();
		tran.commit();
	}
	//删除文章
	public void del(int artId){
		Session session = sessionFactory.openSession();
		Transaction tran =session.beginTransaction();	
		Query query = session.createQuery("delete from Article where artId=?");
		query.setParameter(0, artId);
		query.executeUpdate();
		tran.commit();
	}
	//查询所有文章
	public List<Article> findAll(){
		Session session = sessionFactory.openSession();
		Transaction tran =session.beginTransaction();
		String hql = "from Article";
		Query query = session.createQuery(hql);
		List<Article> list = query.list();
		tran.commit();
		return list;
		
	}
}
