package com.wonders.xlab.shiro.controller;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wonders.xlab.shiro.entity.User;
import com.wonders.xlab.shiro.repository.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mars on 16/7/7.
 */
@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object login(String userName, String password) {
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        final Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
            subject.login(token);
            result.put("ret_code", 0);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("ret_code", -1);
            result.put("message", "帐号或密码错误");
        }


        return result;
    }

    @RequiresRoles("admin")
    @RequiresAuthentication
    @RequestMapping(value = "restPassword", method = RequestMethod.POST)
    public Object restPassword(String userName, String password) {
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        User user = userRepository.findByUserName(userName);

        if (user == null) {
            result.put("ret_code", -1);
        }
        user.setPassword(password);
        userRepository.save(user);
        result.put("ret_code", 0);
        return result;
    }

}
