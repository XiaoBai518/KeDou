package com.kedou.shiro;


import java.util.Map;

import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;

import com.kedou.entity.Admin;
import com.kedou.entity.Business;
import com.kedou.entity.User;

public class PointRealmAuthorization extends ModularRealmAuthorizer {

	 /** 
     * 存放realm 
     */  
    private Map<String, Object> definedRealms;  
  
    public void setDefinedRealms(Map<String, Object> definedRealms) {  
        this.definedRealms = definedRealms;  
    }  
    
    public Map<String, Object> getDefinedRealms() {
		return definedRealms;
	}

    @Override
    public boolean hasRole(PrincipalCollection principals, String role) {
          Object primaryPrincipal = principals.getPrimaryPrincipal();
          for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)) continue;
            if (primaryPrincipal instanceof String) {
                if (realm instanceof UserShiroRealm) {
                    return ((UserShiroRealm) realm).hasRole(principals, role);
                }
            }
            if (primaryPrincipal instanceof Admin) {
                if (realm instanceof AdminShiroRealm) {
                    return ((AdminShiroRealm) realm).hasRole( principals, role);
                }
            }
            if (primaryPrincipal instanceof Business) {
                if (realm instanceof BusinessShiroRealm) {
                    return ((BusinessShiroRealm) realm).hasRole(principals, role);
                }
            }
        }
        return false;
    	
    }
	
}
