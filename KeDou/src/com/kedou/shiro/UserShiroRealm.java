package com.kedou.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;


import com.kedou.entity.User;
import com.kedou.service.user.UserServiceImpl;
import com.kedou.util.Constants;

public class UserShiroRealm extends AuthorizingRealm {

	private UserServiceImpl userServiceImpl;
	
	
	public UserServiceImpl getUserServiceImpl() {
		return userServiceImpl;
	}

	public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}  
	/*** 
      * 获取授权信息 
      */  
     //根据自己系统规则的需要编写获取授权信息，这里为了快速入门只获取了用户对应角色的资源url信息 
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		System.out.println("获取授权信息");
		 String username = (String)pc.fromRealm(getName()).iterator().next();
         if( username!=null){
        	 
             String role = null;
			try {
				role = userServiceImpl.getRoleNameByAccount(username);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
             if(role!=null && !role.isEmpty()){
                 SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                 
                     //将权限资源添加到用户信息中  
                     info.addRole(role);
                  return info;
             }
         }
		return null;
	}
   /*** 
     * 获取认证信息 
     */  
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken at) throws AuthenticationException {
		System.out.println("获取认证信息");
		UsernamePasswordToken token = (UsernamePasswordToken) at;
		
        // 通过表单接收的用户名 
        String username  = token.getUsername();
        if(username!=null && !"".equals(username)){
            User user = null;
			try {
				user = userServiceImpl.findByAcount(username);
				 if (user != null) {
		            	//账号被锁定
		 				if(user.getState()!=Constants.USER_STATE_ACTIVE) {
		 					throw  new LockedAccountException();
		 				}else {
		 					SecurityUtils.getSubject().getSession().setAttribute("loginUser", user);
			                    return new SimpleAuthenticationInfo(username, user.getUserPwd(),ByteSource.Util.bytes(user.getSalt()),getName()); 
		 				}
		            	
		              }else {
		            	  //账号不存在
		            	  throw new UnknownAccountException();
		              }
			} catch (Exception e) {
				e.printStackTrace();
			}
            
        }
		return null;
	}

}
