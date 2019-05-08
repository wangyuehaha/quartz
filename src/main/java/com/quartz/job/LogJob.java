package com.quartz.job;

import com.quartz.service.UserService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 定时打印日志job
 * </p>
 *
 * @author WangYue
 * @date 2019/5/6 17:51
 */
public class LogJob implements Job {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    String name;
    UserService userService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("---------->>>>当前时间：" + simpleDateFormat.format(new Date()));
        System.out.println("---------->>>>name=" + name);
        System.out.println("---------->>>>userService" + userService.toString());
        System.out.println("---------->>>>userService.getUserName" + userService.getUserName());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
