
package com.codeuptopia.secondhandmarket.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.codeuptopia.secondhandmarket.security.AuthRealm;
import com.codeuptopia.secondhandmarket.security.JwtFilter;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.realm.Realm;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

public class SecurityModule extends ShiroWebModule {
    
    private final AppConfig appConfig;
    
    public SecurityModule(ServletContext servletContext, AppConfig appConfig) {
        super(servletContext);
        this.appConfig = appConfig;
    }
    
    @Override
    protected void configureShiroWeb() {
        bindRealm().to(AuthRealm.class).in(Singleton.class);
        addFilterChain("/api/authenticate", AUTHC_BASIC);
        addFilterChain("/api/**", AUTHC_BEARER);
    }
    
    @Provides
    @Singleton
    public Map<String, Realm> provideRealms(AuthRealm authRealm) {
        Map<String, Realm> realms = new HashMap<>();
        realms.put("authRealm", authRealm);
        return realms;
    }
    
    @Provides
    @Singleton
    public JwtFilter provideJwtFilter(JwtUtil jwtUtil) {
        return new JwtFilter(jwtUtil, appConfig.getJwtSecret());
    }
    
}
