
package com.codeuptopia.secondhandmarket.security;

import com.google.inject.Inject;
import com.codeuptopia.secondhandmarket.model.User;
import com.codeuptopia.secondhandmarket.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

public class AuthRealm extends AuthenticatingRealm {
    
    private final UserService userService;
    
    @Inject
    public AuthRealm(UserService userService) {
        this.userService = userService;
    }
    
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UnknownAccountException("Invalid username or password");
        }
        ByteSource salt = ByteSource.Util.bytes(user.getSalt());
        return new SimpleAuthenticationInfo(username, user.getPassword(), salt, getName());
    }
    
}
