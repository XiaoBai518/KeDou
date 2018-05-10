package com.kedou.dao;

import javax.annotation.Resource;


import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.dao.framework.BaseDao;
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
		/**
		 * 添加用户角色项
		 * @param urr
		 * @return
		 * @throws Exception
		 */
		public int saveUserRoleRelation(UserRoleRelation urr)throws Exception {
			SessionFactory sessionFactory = super.getSessionFactory();
			sessionFactory.getCurrentSession().save(urr);
			
			return urr.getUrrId();
			
		}

}
