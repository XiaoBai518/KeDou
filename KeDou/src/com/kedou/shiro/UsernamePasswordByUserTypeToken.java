package com.kedou.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UsernamePasswordByUserTypeToken extends UsernamePasswordToken {
	private static final long serialVersionUID = 7314356955313363823L;
	
	
	/* 
     * 用户类型 
     * 1:用户
     * 2:管理员
     * 3:商家
     */  
    private String userType;  
  
    public String getUserType() {  
        return userType;  
    }  
  
    public void setUserType(String userType) {  
        this.userType = userType;  
    }  
  
    public UsernamePasswordByUserTypeToken(String username, String password, String userType) {  
        super(username, password);  
        this.userType = userType;  
    }  
}
