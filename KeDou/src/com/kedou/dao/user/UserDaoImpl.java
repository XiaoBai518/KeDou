package com.kedou.dao.user;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.User;
import com.kedou.entity.UserRoleRelation;

@Repository
public class UserDaoImpl extends BaseDao<User>{
	
	public List<User> findUserListByUserIdlist(List<Integer> useridList)throws Exception {
		String hql = "from User where userId in (:userIdList)";
		
		Query query = super.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameterList("userIdList", useridList);
		return query.list();
	}
	 /**
		 * 
		 * @desc 通过电话或者邮箱查询用户
		 * @author zhangtianrun
		 * @createDate 2018年3月29日
		 * @return User
		 * @throws Exception
		 */
		public User findByUsername(String teloremail) throws Exception{
			String hql ="from User where userTel=:tel or userEmail=:mail";
			Query query=super.getSessionFactory().getCurrentSession().createQuery(hql);
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
		/**
		 * 更新用户密码
		 * @param u
		 * @throws Exception
		 */
		public void updateUserPwd(Object [] params)throws Exception {
			String hql = "update User u set u.userPwd= ? , u.salt= ? where u.userId=?";
			
			super.updateByProperty(hql, params);
		}

	
	/**
	 * 更新数据库邮件激活码字段
	 * @param params
	 * @throws Exception 
	 */
	public void updateVerifyNum(Object [] params) throws Exception {
		String hql = "update User as u set u.verifyNum =? where u.userId = ?";
		
			super.updateByProperty(hql, params);
		
	}
	/**
	 * 更新用户信息(用户更改信息)
	 * @param state
	 * @throws Exception 
	 */
	public User updateUserMessage(Object [] params) throws Exception{
		
		String hql = "update User as u set u.userName= ?,u.gender=?,u.userDiscription=? where u.userId =?";
		
		super.updateByProperty(hql, params);
		return super.get((Integer)params[params.length-1]);
		
	}
	/**
	 * 更改账户状态
	 * @param state
	 * @throws Exception 
	 */
	public void changeState(Integer params[]) throws Exception {
		
		String hql = "update User as u set u.state= ? where u.userId =?";
			super.updateByProperty(hql, params);
	}
	/**
	 * 更新用户登录信息 （登陆时间 上次登录时间 登录次数）
	 * @param u
	 * @return 更新成功 返回 用户ID 否则 返回 -1
	 */
	public void updateLoginInfo(Object [] params) throws Exception{
		String hql = "update User as u set u.loginTime= ? , u.lastLoginTime= ?,u.loginCount= ?,u.lastLoginIp= ?,u.userIp= ? where u.userId=?";
		
			super.updateByProperty(hql, params);
	}
	/**
	 * 更新用户头像
	 * @param u
	 * @throws Exception
	 */
	public void updateUserIcon(Object [] params)throws Exception {
		String hql = "update User as u set u.userIcon= ? where u.userId=?";
		
		super.updateByProperty(hql, params);
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
	 * @desc 保存label
	 * @author yuyueming
	 * @param code
	 * @return User
	 * @throws Exception
	 */	
	public int saveLabel(String label,int id){
		SessionFactory sessionFactory = super.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("update User u set u.userLabel=? where u.userId=?");
		query.setParameter(0, label);
		query.setParameter(1, id);
		int line = query.executeUpdate();				
		return line;
		
	}
	/**
	 * 更改用户手机号
	 * @param u
	 * @throws Exception
	 */
	public void updateUserTelNum(Object [] params)throws Exception {
		String hql = "update User u set u.userTel= ? where u.userId=?";
		super.updateByProperty(hql, params);
	}
	/**
	 * 绑定用户邮箱
	 * @param u
	 * @throws Exception
	 */
	public void bingUserMail(Object [] params)throws Exception {
		String hql = "update User u set u.userEmail= ? where u.userId=?";
		super.updateByProperty(hql, params);
	}


}
