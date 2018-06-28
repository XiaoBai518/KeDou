package com.kedou.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.kedou.entity.Admin;
import com.kedou.entity.User;
import com.kedou.service.bg.AdminServiceImpl;

public class AdminShiroRealm extends AuthorizingRealm {
	
	private AdminServiceImpl adminServiceImpl;
	
	public AdminServiceImpl getAdminServiceImpl() {
		return adminServiceImpl;
	}

	public void setAdminServiceImpl(AdminServiceImpl adminServiceImpl) {
		this.adminServiceImpl = adminServiceImpl;
	}

	/*** 
     * 获取授权信息 
     */  
    //根据自己系统规则的需要编写获取授权信息，这里为了快速入门只获取了用户对应角色的资源url信息 
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		System.out.println("开始授权信息");
//		 String adminaccount = (String)pc.fromRealm(getName()).iterator().next();
		Admin admin = (Admin)pc.getPrimaryPrincipal();
        if(admin!=null){
        	
            String role = null;
			try {
				role = adminServiceImpl.getRoleNameByAccount(admin.getAdminAccount());
			} catch (Exception e) {
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

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken at) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) at;
		
		System.out.println("shiro认证");
       // 通过表单接收的用户名 
       String adminname  = token.getUsername();
       if(adminname!=null && !"".equals(adminname)){
          Admin admin = null;
			try {
				admin = adminServiceImpl.findAdminAccount(adminname);
			} catch (Exception e) {
				e.printStackTrace();
			}
            if (admin != null) {   
            	SecurityUtils.getSubject().getSession().setAttribute("loginAdmin", admin);
                   return new SimpleAuthenticationInfo(admin, admin.getAdminPwd(),ByteSource.Util.bytes(admin.getAdminSalt()),getName());  
           
             }else {
            	 //账号不存在
           	  	throw new UnknownAccountException();
             }
       }
		return null;
	}

}
