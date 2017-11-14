package com.kaishengit.crm.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Quartz框架的任务
 * @author fankay
 */
public class MyQuartzJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("-----------------------------");
        //取值
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        Integer accountId = dataMap.getInt("accountId");//dataMap.getIntegerFromString("accountId");

        System.out.println("Quartz Running....." + accountId);
    }
}
