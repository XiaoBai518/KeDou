package com.kedou.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.Label;
import com.kedou.entity.User;
import com.kedou.entity.UserRoleRelation;

@Repository
public class UserDaoImpl extends BaseDao<User>{

	
	 /**
		 * 
		 * @desc 通过电话或者邮箱查询用户
		 * @author zhangtianrun
		 * @createDate 2018年3月29日
		 * @return User
		 * @throws Exception
		 */
		public User findByUsername(String teloremail) throws Exception{
			SessionFactory sessionFactory = super.getSessionFactory();
			String hql ="from User where userTel=:tel or userEmail=:mail";
			Query query=sessionFactory.getCurrentSession().createQuery(hql);
			query.setParameter("tel", teloremail);
			query.setParameter("mail", teloremail);
			return (User)query.uniqueResult();
		}
		/**
		 * @desc 通过激活验证码查询用户	
		 * @param code
		 * @return User
		 * @throws Exception
		 */
		public User findByVerifyNum(String code) throws Exception {

			SessionFactory sessionFactory = super.getSessionFactory();
			String hql ="from User where verifyNum=?";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setParameter(0, code);
			return (User)query.uniqueResult();
		}
		public int saveUserRoleRelation(UserRoleRelation urr)throws Exception {
			SessionFactory sessionFactory = super.getSessionFactory();
			sessionFactory.getCurrentSession().save(urr);
			
			return urr.getUrrId();
			
		}
		
		/**
		 * @desc 用户注册后保存个人描述
		 * @author yuyueming
		 * @param code
		 * @return User
		 * @throws Exception
		 */		
		public int saveDis(String dis,int id){
			SessionFactory sessionFactory = super.getSessionFactory();
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("update User u set u.userDiscription=? where u.userId=?");
			query.setParameter(0, dis);
			query.setParameter(1, id);
			int line = query.executeUpdate();				
			return line;			
		}
		
		/**
		 * @desc 查询label
		  * @author yuyueming
		 * @param code
		 * @return User
		 * @throws Exception
		 */	
		public List<Label> showUserLabel(){
			List<Label> label = new ArrayList<Label>();
			SessionFactory sessionFactory = super.getSessionFactory();
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from Label");
			label = query.list();
			return label;
		}
		
		/**
		 * @desc 查询label
		 * @author yuyueming
		 * @param code
		 * @return User
		 * @throws Exception
		 */	
		public int saveLabel(String label,int id){
			SessionFactory sessionFactory = super.getSessionFactory();
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("update User u set u.userTag=? where u.userId=?");
			query.setParameter(0, label);
			query.setParameter(1, id);
			int line = query.executeUpdate();				
			return line;
			
		}
}
