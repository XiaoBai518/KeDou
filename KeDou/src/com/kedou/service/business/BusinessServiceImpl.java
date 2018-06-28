package com.kedou.service.business;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.dao.business.BusinessDaoImpl;
import com.kedou.dao.role.BusinessRoleRelationDaoImpl;
import com.kedou.dao.role.RoleDaoImpl;
import com.kedou.entity.Business;
import com.kedou.entity.BusinessRoleRelation;
import com.kedou.entity.UserRoleRelation;
import com.kedou.service.visitNumber.VisitNumberServiceImpl;
import com.kedou.util.BCrypt;
import com.kedou.util.Constants;

@Service
@Transactional(readOnly=false)
public class BusinessServiceImpl {

	@Resource
	private BusinessDaoImpl businessDaoImpl;
	@Resource
	private BusinessRoleRelationDaoImpl businessRoleRelationDaoImpl;
	@Resource
	private RoleDaoImpl roleDaoImpl;
	@Resource
	private VisitNumberServiceImpl visitNumberServiceImpl;

	
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
	 * @desc 机构详情修改（商家个人中心）
	 * @author 张天润
	 * @createDate 2018年3月29日
	 * @return
	 * @throws Exception
	 */
	
	public void updateBusInfo(String key,String value,String busid) {
		
		Object [] params = {key,value,Integer.parseInt(busid)};
		this.businessDaoImpl.updateBusInfo(params);
		
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
			int busid=bus.getBusId();
			//创建商家角色联系实体
			  BusinessRoleRelation brr = new BusinessRoleRelation();
			  
				  //设置用户角色联系的用户ID
					brr.setBusId(busid);
				     brr.setRoleId(Constants.BUSINESS_ROLEID);
				this.businessRoleRelationDaoImpl.save(brr);
			return busid;
		
	}
	/**
	 * 商家登陆
	 * @author 张天润
	 * @param busAccount
	 * @param busPwd (由前台传来的 用户输入的密码)
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	public Business login(Business bus) throws Exception{
				 //设置登录次数
				     bus.setLoginCount(bus.getLoginCount()+1);
				 //设置用户上次登录时间
				     bus.setLastLoginTime(bus.getLoginTime());
				 //设置用户登录时间
				     bus.setLoginTime(new Date());
				 this.updateLoginInfo(bus);	
				return bus;//可以登陆
		
	}
	/**
	 * 通过账号查询商家角色名
	 * @param roleName
	 * @return
	 * @throws Exception 
	 */
	public String getRoleNameByAccount(String businessname) throws Exception {
		
		//通过用户账号 查找用户Id
		int busid = this.businessDaoImpl.findByAcount(new String [] {businessname}).getBusId();
		//通过用户ID 查找角色Id
		int roleid = this.businessRoleRelationDaoImpl.getRoleidByBusid(busid);
		//通过角色Id 查找角色名字
		String rolename = this.roleDaoImpl.get(roleid).getRoleName();
		return rolename;
		
	}
	/**
	 * 更新用户登录信息 （登陆时间 上次登录时间 登录次数）
	 * @param u
	 * @return  返回 用户ID 
	 */
	public int updateLoginInfo(Business bus) throws Exception{
		Object [] params = {bus.getLoginTime(),bus.getLastLoginTime(),bus.getLoginCount(),bus.getLastLoginIp(),bus.getBusIp(),bus.getBusId()};
			this.businessDaoImpl.updateLoginInfo(params);
		return bus.getBusId();
	}

}
