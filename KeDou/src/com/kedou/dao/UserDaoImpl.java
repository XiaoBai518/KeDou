package com.kedou.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.Collection;
import com.kedou.entity.History;
import com.kedou.entity.Label;
import com.kedou.entity.Torder;
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
		 /**
		 * 
		 * @desc 通过用户ID查询用户
		 * @author 陈
		 * @createDate
		 * @return User
		 * @throws Exception
		 */
	public User findByUserId(int userid) throws Exception{
		SessionFactory sessionFactory = super.getSessionFactory();
		String hql ="from User where userid = :id";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", userid);
		return (User)query.uniqueResult();
	}
	/**
	    * 
		* @desc 通过用户ID查询收集课程
		* @author 陈
		* @createDate
		* @return User
		* @throws Exception
		*/
	public List<Collection> findCollectionByUserId(int userid) throws Exception{
		SessionFactory sessionFactory = super.getSessionFactory();
		String hql ="from Collection where userId = :id";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", userid);
		return query.list();
	}
	/**
	    * 
		* @desc 通过用户ID查询访问历史课程
		* @author 陈
		* @createDate
		* @return User
		* @throws Exception
		*/
	public List<History> findHistoryByUserId(int userid) throws Exception{
		SessionFactory sessionFactory = super.getSessionFactory();
		String hql ="from History where userId = :id";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", userid);
		return query.list();
	}
	/**
	    * 
		* @desc 通过用户ID查询预约课程
		* @author 陈
		* @createDate
		* @return User
		* @throws Exception
		*/
	public List<Torder> findYuyueByUserId(int userid) throws Exception{
		SessionFactory sessionFactory = super.getSessionFactory();
		String hql ="from Torder where userId = :id";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", userid);
		return query.list();
	}
	/**
	    * 
		* @desc 通过课程ID和用户Id删除收集课程
		* @author 陈
		* @createDate
		* @return User
		* @throws Exception
		*/
	public void deleteCollectionByCourseId(int courseid, int userid) throws Exception{
		SessionFactory sessionFactory = super.getSessionFactory();
		String hql ="delete Collection  where courseId = :id and userId = :userid";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", courseid);
		query.setParameter("userid", userid);
		query.executeUpdate();
	}
	/**
	    * 
		* @desc 通过课程ID和用户Id删除收集课程
		* @author 陈
		* @createDate
		* @return User
		* @throws Exception
		*/
	public void deleteYuyueByCourseId(int courseid, int userid) throws Exception{
		SessionFactory sessionFactory = super.getSessionFactory();
		String hql ="delete Torder  where courseId = :id and userId = :userid";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", courseid);
		query.setParameter("userid", userid);
		query.executeUpdate();
	}
	/**
	    * 
		* @desc 通过课程ID和用户Id删除收集课程
		* @author 陈
		* @createDate
		* @return User
		* @throws Exception
		*/
	public void deleteHistoryByCourseId(int courseid, int userid) throws Exception{
		SessionFactory sessionFactory = super.getSessionFactory();
		String hql ="delete History  where courseId = :id and userId = :userid";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", courseid);
		query.setParameter("userid", userid);
		query.executeUpdate();
	}
	/**
	    * 
		* @desc 收藏课程分页查询
		* @author 陈
		* @createDate
		* @return User
		* @throws Exception
		*/
    public List<Collection> findByPage(int pageNum, int pageSize,int userid) throws Exception {
    	SessionFactory sessionFactory = super.getSessionFactory();
    	String hql ="from Collection where userId= :userid";
    	Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setFirstResult((pageNum-1)*pageSize);
		query.setMaxResults(pageSize);
		query.setParameter("userid", userid);
		return query.list();
	}
	/**
	    * 
		* @desc 预约程分页查询
		* @author 陈
		* @createDate
		* @return User
		* @throws Exception
		*/
    public List<Torder> findByPageYuyue(int pageNum, int pageSize,int userid) throws Exception {
    	SessionFactory sessionFactory = super.getSessionFactory();
    	String hql ="from Torder where userId= :userid";
    	Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setFirstResult((pageNum-1)*pageSize);
		query.setMaxResults(pageSize);
		query.setParameter("userid", userid);
		return query.list();
	}
	/**
	    * 
		* @desc 收藏课程分页查询
		* @author 陈
		* @createDate
		* @return User
		* @throws Exception
		*/
    public List<History> findByPageHistory(int pageNum, int pageSize,int userid) throws Exception {
    	SessionFactory sessionFactory = super.getSessionFactory();
    	String hql ="from History where userId= :userid";
    	Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setFirstResult((pageNum-1)*pageSize);
		query.setMaxResults(pageSize);
		query.setParameter("userid", userid);
		return query.list();
	}
	/**
	    * 
		* @desc 根据用户id查询其收藏课程的总数
		* @author 陈
		* @createDate
		* @return User
		* @throws Exception
		*/
    public long findCollectCount(int userid) throws Exception {
    	SessionFactory sessionFactory = super.getSessionFactory();
    	String hql ="select count(*) from Collection where userId= :userid";
    	Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userid", userid);
		return (long)query.uniqueResult();
	}
	/**
	    * 
		* @desc 根据用户id查询其预约课程的总数
		* @author 陈
		* @createDate
		* @return User
		* @throws Exception
		*/
    public long findYuyueCount(int userid) throws Exception {
    	SessionFactory sessionFactory = super.getSessionFactory();
    	String hql ="select count(*) from Torder where userId= :userid";
    	Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userid", userid);
		return (long)query.uniqueResult();
	}		/**
	    * 
		* @desc 根据用户id查询其历史访问课程的总数
		* @author 陈
		* @createDate
		* @return User
		* @throws Exception
		*/
    public long findHistoryCount(int userid) throws Exception {
    	SessionFactory sessionFactory = super.getSessionFactory();
    	String hql ="select count(*) from History where userId= :userid";
    	Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userid", userid);
		return (long)query.uniqueResult();
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
		Query query = session.createQuery("update User u set u.userLabel=? where u.userId=?");
		query.setParameter(0, label);
		query.setParameter(1, id);
		int line = query.executeUpdate();				
		return line;
		
	}

}
