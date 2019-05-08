package com.quartz;

import com.quartz.job.LogJob;
import com.quartz.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuartzApplicationTests {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private UserService userService;

    /**
     * 创建并启动任务
     * @throws Exception
     */
    @Test
    public void addAndStartJob() throws Exception {

        //开始调度任务
        scheduler.start();

        //创建定时任务jobDetail
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("userService", userService);
        jobDataMap.put("name", "贾紫君");
        JobDetail logJobDetail = JobBuilder.newJob(LogJob.class).withIdentity("logJob", "wy").usingJobData(jobDataMap).withDescription("日志定时任务").build();

        //创建cron表达式
        CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");

        //创将调度器
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("logTrigger" ,"wy").withSchedule(cronSchedule).build();

        scheduler.scheduleJob(logJobDetail, cronTrigger);
    }

    /**
     * 修改任务
     * @throws Exception
     */
    @Test
    public void updateJob() throws Exception {
        //获取调度器 triggerkey
        TriggerKey triggerKey = TriggerKey.triggerKey("logTrigger" ,"wy");

        //重新定义cron定时时间
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?")
                .withMisfireHandlingInstructionDoNothing();

        //获取调度器
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        //重新设置调度器
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                .withDescription("修改了定时时间")
                .withSchedule(cronScheduleBuilder).build();

        scheduler.rescheduleJob(triggerKey, trigger);
    }

    /**
     * 删除任务
     * @throws Exception
     */
    @Test
    public void deleteJob() throws Exception {
        //删除定时任务：1.定时触发器 2.定时任务 3.删除任务
        scheduler.pauseTrigger(TriggerKey.triggerKey("logTrigger", "wy"));
        scheduler.unscheduleJob(TriggerKey.triggerKey("logJob" ,"wy"));
        scheduler.deleteJob(JobKey.jobKey("logJob" ,"wy"));
    }

    /**
     * 暂定任务
     * @throws Exception
     */
    @Test
    public void pauseJob() throws Exception {
        scheduler.pauseJob(JobKey.jobKey("logJob" ,"wy"));
    }

    /**
     * 恢复任务
     * @throws Exception
     */
    @Test
    public void resumeJob() throws Exception {
        scheduler.resumeJob(JobKey.jobKey("logJob" ,"wy"));
    }

}
