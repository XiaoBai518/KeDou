package com.kedou.shiro;



import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.util.SimpleByteSource;

import com.kedou.util.BCrypt;

public class UserCredentialsMatcher extends SimpleCredentialsMatcher {



	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		
		 UsernamePasswordToken usertoken = (UsernamePasswordToken)token;
		 //用户登录输入的密码
		 String tokenPwd = String.valueOf(usertoken.getPassword());
		 
		 SimpleAuthenticationInfo userinfo = (SimpleAuthenticationInfo)info;
		
		 //用户真实密码
		 String realPwd = String.valueOf(getCredentials(info));
		 
		//用户 salt 值
		 SimpleByteSource sbs = (SimpleByteSource) userinfo.getCredentialsSalt();
		 String salt = new String(sbs.getBytes());
		 
         return BCrypt.checkpw(tokenPwd+salt, realPwd);
     			
	}
}
