package com.kedou.dao.business;

import org.springframework.stereotype.Repository;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.Business;

@Repository
public class BusinessDaoImpl  extends BaseDao<Business>  {
	
	/**
	 * 
	 * @desc 机构审核通过
	 * @author 原源
	 * @createDate 2018年3月29日
	 * @return
	 * @throws Exception
	 */
	public void applyPass(Object[] params)throws Exception {
		String hql = "update Organization as b set b.busState = ? where b.id = ?";
			super.updateByProperty(hql,params);
		
	}
	/**
	 * 
	 * @desc 通过账号查找商家
	 * @author zhangtianrun
	 * @createDate 2018年3月28日
	 * @return User
	 */
	public Business findByAcount(String [] params) throws Exception{
		String hql = " from Business  where busAccount=?";
		return super.findOne(hql, params);
			
	}
	/**
	 * 
	 * @desc 机构锁定
	 * @author 原源
	 * @createDate 2018年3月29日
	 * @return
	 * @throws Exception
	 */
	public void busLock(Object [] params) throws Exception {
		String hql = "update Organization as b set b.busState = ? where b.id = ?";
		
			super.updateByProperty(hql,params);
		

	}
	
}
