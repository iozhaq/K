package com.kaishengit;

import com.kaishengit.crm.jobs.MyQuartzJob;
import org.junit.Test;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;

public class QuartzTest {

    @Test
    public void simpleTrigger() throws SchedulerException, IOException {
        //定义Job
        JobDetail jobDetail = JobBuilder.newJob(MyQuartzJob.class).build();
        //定义Trigger
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
        scheduleBuilder.withIntervalInSeconds(5); //每隔5秒中执行
        scheduleBuilder.repeatForever();//永远重复
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(scheduleBuilder).build();
        //创建调度者对象
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.scheduleJob(jobDetail,trigger);
        scheduler.start();

        System.in.read();
    }

    @Test
    public void cronTrigger() throws SchedulerException, IOException {
        //设定JobDetail中的参数
        JobDataMap dataMap = new JobDataMap();
        //dataMap.putAsString("accountId",1000);
        dataMap.put("accountId",1200);
        //定义Job
        JobDetail jobDetail = JobBuilder
                .newJob(MyQuartzJob.class)
                .setJobData(dataMap)
                .build();
        //定义Trigger
        ScheduleBuilder scheduleBuilder =
                CronScheduleBuilder.cronSchedule("0/2 * * * * ? "); //!!!! Cron表达式
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(scheduleBuilder).build();
        //创建调度者对象
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.scheduleJob(jobDetail,trigger);
        scheduler.start();

        System.in.read();
    }
}
