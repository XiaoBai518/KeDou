package com.kedou.service.user;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kedou.dao.role.RoleDaoImpl;
import com.kedou.dao.role.UserRoleRelationDaoImpl;
import com.kedou.dao.user.LabelDaoImpl;
import com.kedou.dao.user.UserDaoImpl;
import com.kedou.entity.Label;
import com.kedou.entity.User;
import com.kedou.entity.UserRoleRelation;
import com.kedou.shiro.UsernamePasswordByUserTypeToken;
import com.kedou.util.BCrypt;
import com.kedou.util.Constants;
import com.kedou.util.IpAddress;
import com.kedou.util.SendEmailThread;

@Service
@Transactional(readOnly=false)
public class UserServiceImpl {
	@Resource
	private UserDaoImpl userDaoImpl;
	@Resource
	private LabelDaoImpl labelDaoImpl; //标签Dao
	@Resource
	private UserRoleRelationDaoImpl userRoleRelationDaoImpl;
	@Resource
	private RoleDaoImpl roleDaoImpl;
	
	/**
	 * 
	 * @desc 注册用户
	 * @author yuanyuan
	 * @createDate 2018年3月28日
	 * @return 用户主键
	 * @throws Exception
	 */
	public int registerUser(User u) throws Exception{
		//向用户输入的邮箱发送验证邮件 并返回激活验证码
		String code = this.sendEmail(u.getUserEmail());
		   //添加激活验证码
			 u.setVerifyNum(code);
		   //设置账户状态为未激活状态
			 u.setState(0);
			 
		//随机生成 salt
		String salt =UUID.randomUUID().toString().replace("-", "");
		   //进行二次加密
		 u.setUserPwd(BCrypt.hashpw(u.getUserPwd()+salt, BCrypt.gensalt()));
		   //设置salt值
		     u.setSalt(salt);
		 //获取当地时间  并设置为用户创建时间
		     u.setCreateTime(new Date());
		 //设置用户登录次数为 0
		     u.setLoginCount(0);
		  
		 //创建用户角色联系实体
		  UserRoleRelation urr = new UserRoleRelation();
		     
		
			this.userDaoImpl.save(u);
			  //设置用户角色联系的用户ID
				
			     urr.setUserId(u.getUserId());
			  //设置用户角色联系的角色ID
			     urr.setRoleId(1);
			     this.userRoleRelationDaoImpl.save(urr);
			return u.getUserId();
	}
	/**
	 * @desc 发送激活邮件
	 * @author yuanyuan
	 * @createDate 2018年3月28日
	 * @return 用户激活标识码
	 * 
	 */
	public  String  sendEmail(String emailAddress){  
		//生成用户code
        String code=UUID.randomUUID().toString().replace("-", "");
		new SendEmailThread(emailAddress,code).start();  //向用户注册的邮箱发送验证码
		return code;
    }  
	/**
	 * 重新发送激活邮件
	 * @param emailAddress
	 * @param u
	 * @return
	 * @throws Exception 
	 */
	public void resendEmail(String emailAddress,User u) throws Exception {
		//向用户输入的邮箱发送验证邮件 并返回激活验证码
				String code = this.sendEmail(u.getUserEmail());
				
				   //添加激活验证码
					 u.setVerifyNum(code);
		//更新数据库邮件激活码字段
			Object [] params = {code,u.getUserId()};
			
				this.userDaoImpl.updateVerifyNum(params);
		
	}
	/**
	 * 
	 * @desc 通过邮箱或电话查找用户
	 * @author zhangtianrun
	 * @createDate 2018年3月28日
	 * @return User
	 */
	public User findByAcount(String username)throws Exception  {
		
			return this.userDaoImpl.findByUsername(username);
		
	}
	public String getSalt(String username) throws Exception {
		User u =this.userDaoImpl.findByUsername(username);
		if(u!=null) {
			return u.getSalt();
		}
		return null;
	}
	/**
	 * 更新用户信息(用户更改信息)
	 * @param state
	 * @throws Exception 
	 */
	public User updateUserMessage(String username,String gender,String userDiscription,int id) throws Exception{
		Object [] params = {username,gender,userDiscription,id};
		
		return this.userDaoImpl.updateUserMessage(params);
	}
	/**
	 * 更改账户状态
	 * @param state
	 * @throws Exception 
	 */
	public void changeState(int state,int id) throws Exception {
		Integer params[] = {state,id};
	
			this.userDaoImpl.changeState(params);
		
		
	}
	/**
	 * 更新用户登录信息 （登陆时间 上次登录时间 登录次数）
	 * @param u
	 * @return  返回 用户ID 
	 */
	public int updateLoginInfo(User u) throws Exception{
		Object [] params = {u.getLoginTime(),u.getLastLoginTime(),u.getLoginCount(),u.getLastLoginIp(),u.getUserIp(),u.getUserId()};
			this.userDaoImpl.updateLoginInfo(params);
		return u.getUserId();
	}
	/**
	 * 通过激活验证码查询用户
	 * @param code
	 * @return
	 */
	public User findByVerifyNum(String code) {
			User u;
		try {
			u=userDaoImpl.findByVerifyNum(code);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			u=null;
		}
		return u;
	}
	/**
	 * 
	 * @desc 通过用户ID查找用户
	 * @author 陈
	 * @createDate 
	 * @return User
	 */
	public User findByUserId(int userid)throws Exception  {
		
			return this.userDaoImpl.get(userid);
		
	}
	/**
	 * 通过账号查询用户角色名
	 * @param roleName
	 * @return
	 * @throws Exception 
	 */
	public String getRoleNameByAccount(String username) throws Exception {
		
		//通过用户账号 查找用户Id
		int userid = this.userDaoImpl.findByUsername(username).getUserId();
		//通过用户ID 查找角色Id
		int roleid = this.userRoleRelationDaoImpl.getRoleidByUserid(userid);
		//通过角色Id 查找角色名字
		String rolename = this.roleDaoImpl.get(roleid).getRoleName();
		return rolename;
		
	}
	/**
	 * 
	 * @desc 登陆
	 * @author kangziyun
	 * @createDate 2018年3月29日
	 * @param String username
	 * @param String pwd
	 * @return -1 0 1
	 *  如果密码错误     返回 -1  
	 *  如果账号不存在  		返回   0
	 *  如果正确            		返回   User
	 *  如果查询错误    	 	返回 -2
	 *  如果账户未激活 		返回 -3
	 *  如果账户被锁定            返回 -4
	 *  如果账户状态值异常     返回 -5
	 * @throws Exception 
	 */
	public User login(User u) throws Exception {
		//设置登录次数
			u.setLoginCount(u.getLoginCount()+1);
		//设置用户上次登录时间
			u.setLastLoginTime(u.getLoginTime());
		//设置用户登录时间
			 u.setLoginTime(new Date());
		this.updateLoginInfo(u);	
		return u;//可以登陆
	}
	/**
	 * 更新用户头像
	 * @param u
	 * @throws Exception
	 */
	public void updateUserIcon(User u)throws Exception {
		Object [] params = {u.getUserIcon(),u.getUserId()};
		this.userDaoImpl.updateUserIcon(params);
	}

	
	/**
	 * @desc 展示用户描述
	 * @author yuyueming
	 * @createDate 2018年5月10日
	 * @return 
	 * 
	 */
	public List<Integer> showDis() {
		Random rand = new Random();  
	    int a = rand.nextInt(10);
	    List<Integer> list = new ArrayList<Integer>();	
	    if(a<=3){
	    	list.add(1);
	    	list.add(4);
	    	list.add(8);
	    	list.add(10);
	    }else if(a>3&&a<=6){
	    	list.add(2);
	    	list.add(3);
	    	list.add(8);
	    	list.add(9);
	    }else if(a>6&&a<=9){
	    	list.add(1);
	    	list.add(5);
	    	list.add(7);
	    	list.add(10);
	    }
		return list;
	}
	/**
	 * @desc 保存用户描述
	 * @author yuyueming
	 * @createDate 2018年5月15日
	 * @return 
	 * 
	 */
	public int saveDis(String dis,int id) {
		int line = this.userDaoImpl.saveDis(dis, id);
		return line;
		
	}
	
	/**
	 * @desc 查询label标签
	 * @author yuyueming
	 * @createDate 2018年5月15日
	 * @return 
	 * 
	 */
	public List<Label> showUserLabel() throws Exception{
		List<Label> label = new ArrayList<Label>();
		label = this.labelDaoImpl.findAll();
		return label;
	}
	/**
	 * @desc 保存label标签
	 * @author yuyueming
	 * @createDate 2018年5月15日
	 * @return 
	 * 
	 */
	public int saveLabel(String label,int id){
		int line = 0;
		line = this.userDaoImpl.saveLabel(label, id);
		return line;
	}


	
}
