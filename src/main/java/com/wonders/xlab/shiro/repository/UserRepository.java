package com.wonders.xlab.shiro.repository;

import com.wonders.xlab.shiro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mars on 16/7/7.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);
}
