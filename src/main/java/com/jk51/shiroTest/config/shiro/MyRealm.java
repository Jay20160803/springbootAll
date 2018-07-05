package com.jk51.shiroTest.config.shiro;


import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018/7/4
 * 修改记录:
 */

public class MyRealm extends AuthorizingRealm {

    /**
     * 下次在继承数据库查询权限和用户，这次直接写死
     * */

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        Set<String> list = new HashSet();
        list.add("user:add");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(list);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken upt = (UsernamePasswordToken) token;
        String username = upt.getUsername();

        Map<String,String> userInfo = new HashMap();
        userInfo.put("username","admin");
        userInfo.put("password","123456");

        return  new SimpleAuthenticationInfo(username, userInfo.get("password"), getName());
    }


}
