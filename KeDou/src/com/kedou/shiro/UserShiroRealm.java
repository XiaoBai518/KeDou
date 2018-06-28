package com.kedou.shiro;

import javax.annotation.Resource;

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
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.kedou.entity.User;
import com.kedou.service.user.UserServiceImpl;
import com.kedou.util.Constants;



public class UserShiroRealm extends AuthorizingRealm {

	@Resource
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
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		System.out.println("获取授权信息");
//		 String username = (String)pc.fromRealm(getName()).iterator().next();
		String username = (String)pc.getPrimaryPrincipal();
         if( pc!=null){
        	 
        	 AuthorizationInfo info = null;
             
             Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
             if (cache != null) { 
                 Object key = getAuthorizationCacheKey(pc);
                 info = cache.get(key);
                 if (info == null) {
                	 //缓存中没有值
                	 //从数据库中查找权限
                	 String role = null;
                	 try {
                		 User user = this.userServiceImpl.findByAcount(username);
                		 role = userServiceImpl.getRoleNameByAccount(user.getAccount());
         			} catch (Exception e) {
         				// TODO 自动生成的 catch 块
         				e.printStackTrace();
         			}
                	 SimpleAuthorizationInfo DBinfo  = new SimpleAuthorizationInfo();
                      //将权限资源添加到用户信息中  
                      DBinfo.addRole(role);
                     cache.put(key, DBinfo);
                     return DBinfo;
                 }
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
