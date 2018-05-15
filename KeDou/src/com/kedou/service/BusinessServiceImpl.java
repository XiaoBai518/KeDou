package com.kedou.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.dao.BusinessCourseTypeRelationDaoImpl;
import com.kedou.dao.BusinessDaoImpl;
import com.kedou.entity.Business;
import com.kedou.entity.CourseType;
import com.kedou.service.common.CommonServiceImpl;
import com.kedou.util.BCrypt;

@Service
@Transactional(readOnly=false)
public class BusinessServiceImpl {

	@Resource
	private BusinessDaoImpl businessDaoImpl;
	@Resource
	private CommonServiceImpl commonServiceImpl;//公共的方法实现

	
	/**
	 * 
	 * @desc 机构审核通过
	 * @author 原源
	 * @createDate 2018年3月29日
	 * @return
	 * @throws Exception
	 */
	public void applyPass(int id) {
		String hql = "update Organization as b set b.busState = ? where b.id = ?";
		Object [] params ={2,id};
		try {
			this.businessDaoImpl.updateByProperty(hql,params);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @desc 查看机构详情
	 * @author 原源
	 * @createDate 2018年3月29日
	 * @return
	 * @throws Exception
	 */
	public Business findById(int id){
		try {
			return this.businessDaoImpl.get(id);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 
	 * @desc 通过账号查找商家
	 * @author zhangtianrun
	 * @createDate 2018年3月28日
	 * @return User
	 */
	public Business findByAcount(String busacount) throws Exception{
		String [] params = {busacount};
		String hql = " from Business  where busAccount=?";
		Business b = this.businessDaoImpl.findOne(hql, params);
			return b;
	}
	/**
	 * 
	 * @desc 机构锁定
	 * @author 原源
	 * @createDate 2018年3月29日
	 * @return
	 * @throws Exception
	 */
	public void busLock(int id) {
		String hql = "update Organization as b set b.busState = ? where b.id = ?";
		Object [] params ={3,id};
		try {
			this.businessDaoImpl.updateByProperty(hql,params);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 * @desc 机构详情修改
	 * @author 原源
	 * @createDate 2018年3月29日
	 * @return
	 * @throws Exception
	 */
	public void updateBus(Business bus) {
		try {
			this.businessDaoImpl.update(bus);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @desc 机构列表查询
	 * @author 原源
	 * @createDate 2018年3月29日
	 * @return
	 */
	public List<Business> findAll() {
		try {
			return this.businessDaoImpl.findAll();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return new ArrayList<Business>();
		}
	}

	/**
	 * 商家注册
	 * @param bus
	 * @return 商家ID
	 */
	public int registe(Business bus) throws Exception{
		//随机生成 salt
				String salt =UUID.randomUUID().toString().replace("-", "");
				//使用 Bcrypt进行二次加密
				String hashedPwd = BCrypt.hashpw(bus.getBusPwd()+salt, BCrypt.gensalt());
				   //设置二次加密后的密码
				     bus.setBusPwd(hashedPwd);
				   //设置salt值
				     bus.setSalt(salt);
				 //获取当地时间  并设置为用户创建时间
				    bus.setCreateTime(new Date());
				 //设置用户登录次数为 0
				    bus.setLoginCount(0);
				  
			businessDaoImpl.save(bus);
			int a=bus.getBusId();
			return a;
		
	}
	/**
	 * 商家登陆
	 * @author 张天润
	 * @param busAccount
	 * @param busPwd (由前台传来的 用户输入的密码)
	 * @param session
	 * @return
	 */
	public Business login(Business bus,String busPwd){
		String salt = bus.getSalt();
		//判断账户状态是否为 可登陆状态
		if(bus.getState()!=1) {
			return bus;
		}else {
			if(BCrypt.checkpw(busPwd+salt,bus.getBusPwd())){
				 //设置登录次数
				     bus.setLoginCount(bus.getLoginCount()+1);
				 //设置用户上次登录时间
				     bus.setLastLoginTime(bus.getLoginTime());
				 //设置用户登录时间
				     bus.setLoginTime(new Date());
				return bus;//可以登陆
			}else{
				return null;//密码错误
			}
		}
	}

}
