package com.kedou.dao.user;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.User;

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


}
