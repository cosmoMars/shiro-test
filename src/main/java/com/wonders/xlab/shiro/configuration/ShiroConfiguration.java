package com.wonders.xlab.shiro.configuration;

import com.wonders.xlab.shiro.realm.UserRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by mars on 16/7/7.
 */
@Configuration
public class ShiroConfiguration {

    /*@Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public UserRealm userRealm() {
        return new UserRealm();
    }


    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(SessionManager sessionManager) {
        final DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager);
//        securityManager.setRememberMeManager(rememberMeManager);
//        securityManager.setSubjectFactory(new MySubjectFactory());
        Collection<Realm> realms = new ArrayList<>();
        realms.add(userRealm());
        securityManager.setRealms(realms);
        return securityManager;
    }*/

    private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

    @Bean(name = "userRealm")
    public UserRealm getShiroRealm() {
        return new UserRealm();
    }

//    @Bean(name = "shiroEhcacheManager")
//    public EhCacheManager getEhCacheManager() {
//        EhCacheManager em = new EhCacheManager();
//        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
//        return em;
//    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager() {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        dwsm.setRealm(getShiroRealm());
//        dwsm.setCacheManager(getEhCacheManager());
        return dwsm;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(getDefaultWebSecurityManager());
        return new AuthorizationAttributeSourceAdvisor();
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean
                .setSecurityManager(getDefaultWebSecurityManager());
        shiroFilterFactoryBean.setLoginUrl("/users/login");
        
        //        shiroFilterFactoryBean.setSuccessUrl("/sa/index");
        filterChainDefinitionMap.put("/sa/**", "authc");
        filterChainDefinitionMap.put("/**", "anon");
        shiroFilterFactoryBean
                .setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }



}
