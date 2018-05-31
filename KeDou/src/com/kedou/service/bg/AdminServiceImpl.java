package com.kedou.service.bg;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.dao.bg.AdminDaoImpl;
import com.kedou.dao.role.AdminRoleRelationDaoImpl;
import com.kedou.dao.role.RoleDaoImpl;
import com.kedou.dao.role.UserRoleRelationDaoImpl;
import com.kedou.dao.user.UserDaoImpl;
import com.kedou.entity.Admin;
import com.kedou.entity.AdminRoleRelation;
import com.kedou.entity.User;
import com.kedou.entity.UserRoleRelation;
import com.kedou.util.BCrypt;
import com.kedou.util.Constants;
@Service
@Transactional(readOnly=false)
public class AdminServiceImpl {


	@Resource
	private AdminDaoImpl adminDaoImpl;
	@Resource
	private UserDaoImpl userDaoImpl;
	@Resource
	private AdminRoleRelationDaoImpl adminRoleRelationDaoImpl;
	@Resource
	private RoleDaoImpl roleDaoImpl;
	
	
	
	/**
	 * 锁定用户
	 * @param array
	 */
	@Transactional
	public void lockUser(int[] array) {
		for(int i=0;i<array.length;i++) {
			try {
				adminDaoImpl.lockUser(array[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	/**
	 * 查询所有User的数据
	 * @return
	 */
	@Transactional
	public List<User> searchAllUser(){
		List<User> list;
		try {
			list = userDaoImpl.findAll();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	/**
	 * 编辑用户
	 * @param u
	 */
	@Transactional
	public void editUser(User u) {
		try {
			userDaoImpl.update(u);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 查找用户
	 * @param account
	 * @return
	 */
	@Transactional
	public User findUser(String account) {
		try {
			return adminDaoImpl.findUser(account);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 查询管理员是否存在
	 * @param account
	 * @return Admin
	 * @throws Exception
	 * @author 宋亚楼
	 */
	public Admin findAdminAccount(String account) {

		try {
			return adminDaoImpl.findAdmin(account);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	/**
	 * 储存管理员
	 * @param ad
	 * @throws Exception
	 * @author 宋亚楼
	 */
	public void saveAdmin(Admin ad) throws Exception {
		adminDaoImpl.save(ad);
	}
	
	
	
	/**
	 * 登录
	 * @param ad
	 * @param pwd
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public Admin adminLogin(Admin ad) throws Exception {

				 //设置登录次数
				     ad.setLoginCount(ad.getLoginCount()+1);
				 //设置用户上次登录时间
				     ad.setLastLoginTime(ad.getLoginTime());
				 //设置用户登录时间
				     ad.setLoginTime(new Date());
				    this.updateLoginInfo(ad);
				return ad;//可以登陆
		
	}
	/**
	 * 更新管理员登录信息 （登陆时间 上次登录时间 登录次数）
	 * @param u
	 * @return  返回 用户ID 
	 */
	public int updateLoginInfo(Admin ad) throws Exception{
		Object [] params = {ad.getLoginTime(),ad.getLastLoginTime(),ad.getLoginCount(),ad.getLastLoginIp(),ad.getLoginIp(),ad.getAdminId()};
			this.adminDaoImpl.updateLoginInfo(params);
		return ad.getAdminId();
	}
	/**
	 * 添加管理员
	 * pwd加密以及admin的部分属性初始化
	 * 2018/05/30已更改
	 * @param ad
	 * @return 
	 * @throws Exception
	 */
	public Admin registerAdmin(Admin ad)throws Exception{
		//生成 salt 值
		String salt=UUID.randomUUID().toString().replace("-", "");
		//二次加密
		String doublePwd = BCrypt.hashpw(ad.getAdminPwd()+salt, BCrypt.gensalt());
		//设置用户密码
		ad.setAdminPwd(doublePwd);
		//设置用户salt 值
		ad.setAdminSalt(salt);
		//设置用户创建时间
		ad.setCreateTime(new Date());
		//设置用户登陆次数为0
		ad.setLoginCount(0);
		
		this.saveAdmin(ad);
		 //创建用户角色联系实体
		  AdminRoleRelation arr = new AdminRoleRelation();
		  
		
			  //设置管理员角色联系的用户ID
			     arr.setAdminId(ad.getAdminId());
			  //设置管理员角色联系的角色ID
			   arr.setRoleId(5);
			     this.adminRoleRelationDaoImpl.save(arr);
		return ad;
	}
	/**
	 * 通过管理员账号查询管理员角色名
	 * @param roleName
	 * @return
	 * @throws Exception 
	 */
	public String getRoleNameByAccount(String adminname) throws Exception {
		
		//通过用户账号 查找管理员Id
		int adminid = this.adminDaoImpl.findAdmin(adminname).getAdminId();
		//通过管理员ID 查找角色Id
		int roleid = this.adminRoleRelationDaoImpl.getRoleidByAdminid(adminid);
		//通过角色Id 查找角色名字
		String rolename = this.roleDaoImpl.get(roleid).getRoleName();
		return rolename;
		
	}
}
