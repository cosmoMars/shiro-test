package com.wonders.xlab.shiro.realm;

import com.wonders.xlab.shiro.entity.User;
import com.wonders.xlab.shiro.repository.UserRepository;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mars on 16/7/7.
 */
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String userName = (String) principalCollection.getPrimaryPrincipal();

        User user = userRepository.findByUserName(userName);

        if (user == null) {
            return null;
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roleSet = new HashSet<>();
        roleSet.add("admin");
        simpleAuthorizationInfo.setRoles(roleSet);

        Set<String> permissionSet = new HashSet<>();
        permissionSet.add("user:*");
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String userName = (String) authenticationToken.getPrincipal();
        User user = userRepository.findByUserName(userName);

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), user.getUserName());

        return simpleAuthenticationInfo;
    }

}
