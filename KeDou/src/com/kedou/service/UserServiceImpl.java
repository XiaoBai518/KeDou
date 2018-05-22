package com.kedou.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.dao.UserDaoImpl;
import com.kedou.entity.Label;
import com.kedou.entity.User;
import com.kedou.entity.UserRoleRelation;
import com.kedou.service.common.CommonServiceImpl;
import com.kedou.util.BCrypt;
import com.kedou.util.SendEmailThread;

@Service
@Transactional(readOnly=false)
public class UserServiceImpl {
	@Resource
	private UserDaoImpl userDaoImpl;
	@Resource
	private CommonServiceImpl commonServiceImpl;//公共的方法实现
	
	/**
	 * 
	 * @desc 注册用户
	 * @author yuanyuan
	 * @createDate 2018年3月28日
	 * @return 用户主键
	 * @throws Exception
	 */
	@Transactional
	public int registerUser(User u,HttpServletRequest request){
		//向用户输入的邮箱发送验证邮件 并返回激活验证码
		String code = this.sendEmail(u.getUserEmail());
		   //添加激活验证码
			 u.setVerifyNum(code);
		   //设置账户状态为未激活状态
			 u.setState(0);
			 
		//随机生成 salt
		String salt =UUID.randomUUID().toString().replace("-", "");
		//使用 Bcrypt进行二次加密
		String hashedPwd = BCrypt.hashpw(u.getUserPwd()+salt, BCrypt.gensalt());
		   //设置二次加密后的密码
		     u.setUserPwd(hashedPwd);
		   //设置salt值
		     u.setSalt(salt);
		     
		 //获取用户真实IP
		 String IP = commonServiceImpl.getIpAddress(request);
		    //设置用户IP
		     u.setUserIp(IP);
		     
		 //获取当地时间  并设置为用户创建时间
		     u.setCreateTime(new Date());
		 //设置用户登录次数为 0
		     u.setLoginCount(0);
		  
		 //创建用户角色联系实体
		  UserRoleRelation urr = new UserRoleRelation();
		     
		try {
			this.userDaoImpl.save(u);
			  //设置用户角色联系的用户ID
			     urr.setUrrId(u.getUserId());
			  //设置用户角色联系的角色ID
			     urr.setRoleId(1);
			this.userDaoImpl.saveUserRoleRelation(urr);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return -1;
		} 
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
	 */
	public int resendEmail(String emailAddress,User u) {
		//向用户输入的邮箱发送验证邮件 并返回激活验证码
				String code = this.sendEmail(u.getUserEmail());
				
				   //添加激活验证码
					 u.setVerifyNum(code);
		//更新数据库邮件激活码字段
			Object [] params = {code,u.getUserId()};
			String hql = "update User as u set u.verifyNum =? where u.userId = ?";
			try {
				this.userDaoImpl.updateByProperty(hql, params);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				return -1;
			}
			return u.getUserId();
	}
	/**
	 * 
	 * @desc 通过邮箱或电话查找用户
	 * @author zhangtianrun
	 * @createDate 2018年3月28日
	 * @return User
	 */
	public User findByAcount(String username)  {
		User u;
		try {
			u = this.userDaoImpl.findByUsername(username);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;//数据库错误
		}
			return u;
	}
	/**
	 * 更改登陆状态
	 * @param state
	 */
	public int changeState(int state,int id) {
		Integer params[] = {state,id};
		String hql = "update User as u set u.state= ? where u.userId =?";
		try {
			userDaoImpl.updateByProperty(hql, params);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return -1;
		}
		return id;
	}
	/**
	 * 更新用户登录信息 （登陆时间 上次登录时间 登录次数）
	 * @param u
	 * @return 更新成功 返回 用户ID 否则 返回 -1
	 */
	public int updateLoginInfo(User u) {
		Object [] params = {u.getLoginTime(),u.getLastLoginTime(),u.getLoginCount()};
		String hql = "update User as u set u.loginTime=? , u.lastLoginTime=?,u.loginCount=? where u.userId=?";
		try {
			this.userDaoImpl.updateByProperty(hql, params);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return -1;
		}
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
	 * @desc 登陆
	 * @author kangziyun
	 * @createDate 2018年3月29日
	 * @param String username
	 * @param String pwd
	 * @param int time 自动登陆时间
	 * @param HttpSession session
	 * @param HttpServletResponse response
	 * @return -1 0 1
	 *  如果密码错误     返回 -1  
	 *  如果账号不存在  		返回   0
	 *  如果正确            		返回   1
	 *  如果查询错误    	 	返回 -2
	 *  如果账户未激活 		返回 -3
	 *  如果账户被锁定            返回 -4
	 *  如果账户状态值异常     返回 -5
	 */
	public int login(String username,String pwd,int time,HttpSession session,HttpServletResponse response,HttpServletRequest request) {
		User us;
		try {
			us = this.userDaoImpl.findByUsername(username);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return -2;//数据库查询错误报错
		}
		if(us!=null){
			String salt = us.getSalt();
			//判断账户状态是否为 可登陆状态
			if(us.getState()!=1) {
				//账户未激活
				 if(us.getState()==-1) {
					 return -3;
				 }
				 //账户被锁定
				 if(us.getState()==2) {
					 return -4;
				 }
				 //状态值异常
				 return -5;
			}else {
				if(BCrypt.checkpw(pwd+salt,us.getUserPwd())){
					 //获取用户真实IP
					 String IP = commonServiceImpl.getIpAddress(request);
					    //设置用户IP
					     us.setUserIp(IP);
					  
					 //设置登录次数
					     us.setLoginCount(us.getLoginCount()+1);
					 //设置用户上次登录时间
					     us.setLastLoginTime(us.getLoginTime());
					 //设置用户登录时间
					     us.setLoginTime(new Date());
					 
					this.commonServiceImpl.autoLogin(us, time, response,session);
					return 1;//可以登陆
				}else{
					return -1;//密码错误
				}
			}
			
		}else{
			return 0;//账号不存在
		}
	}
	/**
	 * 注销
	 * @param u
	 * @param response
	 * @param session
	 */
	public void logout(HttpServletResponse response,HttpSession session){  
	   
	    Cookie userNameCookie = new Cookie("userAcount", null);  
	    Cookie passwordCookie = new Cookie("userPwd", null);  
	    userNameCookie.setMaxAge(0);  
	    userNameCookie.setPath("/"); 
	    
	    passwordCookie.setMaxAge(0);  
	    passwordCookie.setPath("/");  
	    
	    response.addCookie(userNameCookie);  
	    response.addCookie(passwordCookie);  
	      
	    session.removeAttribute("loginUser");  
	      
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
	public List<Label> showUserLabel(){
		List<Label> label = new ArrayList<Label>();
		label = this.userDaoImpl.showUserLabel();
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
