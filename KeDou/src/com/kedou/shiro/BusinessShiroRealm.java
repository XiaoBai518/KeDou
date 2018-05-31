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
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.kedou.entity.Business;
import com.kedou.entity.User;
import com.kedou.service.business.BusinessServiceImpl;
import com.kedou.util.Constants;

public class BusinessShiroRealm extends AuthorizingRealm {

	private BusinessServiceImpl businessServiceImpl;
	
	public BusinessServiceImpl getBusinessServiceImpl() {
		return businessServiceImpl;
	}

	public void setBusinessServiceImpl(BusinessServiceImpl businessServiceImpl) {
		this.businessServiceImpl = businessServiceImpl;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		// TODO 自动生成的方法存根
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
        String busname  = token.getUsername();
        if(busname!=null && !"".equals(busname)){
            Business bus = null;
			try {
				bus = businessServiceImpl.findByAcount(busname);
				 if (bus != null) {
		            	//账号被锁定
		 				if(bus.getState()!=Constants.BUSINESS_STATE_ACTIVE) {
		 					throw  new LockedAccountException();
		 				}else {
		 					SecurityUtils.getSubject().getSession().setAttribute("loginBusiness", bus);
			                    return new SimpleAuthenticationInfo(busname, bus.getBusPwd(),ByteSource.Util.bytes(bus.getSalt()),getName()); 
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
