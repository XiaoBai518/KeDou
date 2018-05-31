package com.kedou.service.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.dao.business.BusinessDaoImpl;
import com.kedou.dao.course.BusinessCourseTypeRelationDaoImpl;
import com.kedou.entity.Business;
import com.kedou.entity.CourseType;
import com.kedou.service.common.CommonServiceImpl;
import com.kedou.util.BCrypt;
import com.kedou.util.Constants;

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
	public void applyPass(int id) throws Exception {
			Object [] params ={Constants.BUSINESS_STATE_ACTIVE,id};
			this.businessDaoImpl.applyPass(params);
		
	}
	
	/**
	 * 
	 * @desc 查看机构详情
	 * @author 原源
	 * @createDate 2018年3月29日
	 * @return
	 * @throws Exception
	 */
	public Business findById(int id) throws Exception{
		
			return this.businessDaoImpl.get(id);
		
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
		
		return this.businessDaoImpl.findByAcount(params);
		
	}
	/**
	 * 
	 * @desc 机构锁定
	 * @author 原源
	 * @createDate 2018年3月29日
	 * @return
	 * @throws Exception
	 */
	public void busLock(int id) throws Exception {
		
		Object [] params ={Constants.BUSINESS_STATE_LOCK,id};
		
		this.businessDaoImpl.busLock(params);
		

	}
	
	/**
	 * 
	 * @desc 机构详情修改
	 * @author 原源
	 * @createDate 2018年3月29日
	 * @return
	 * @throws Exception
	 */
	public void updateBus(Business bus) throws Exception {
		
			this.businessDaoImpl.update(bus);
		
	}
	/**
	 * 
	 * @desc 机构列表查询
	 * @author 原源
	 * @createDate 2018年3月29日
	 * @return
	 * @throws Exception 
	 */
	public List<Business> findAll() throws Exception {
		
			return this.businessDaoImpl.findAll();
		
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
