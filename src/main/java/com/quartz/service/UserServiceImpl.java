package com.quartz.service;

import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author WangYue
 * @date 2019/5/6 18:21
 */
@Service
public class UserServiceImpl implements UserService, Serializable {

    private static final long serialVersionUID = -3419746455730618613L;

    private String name = "王越";

    private String age = "18";

    @Override
    public String getUserName() {
        return "王越";
    }

    @Override
    public String toString() {
        return "UserServiceImpl{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
