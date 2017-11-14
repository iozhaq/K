package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.example.TaskExample;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.jobs.SendMessageJob;
import com.kaishengit.crm.mapper.TaskMapper;
import com.kaishengit.crm.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 待办事项业务层
 * @author fankay
 */
@Service
public class TaskServiceImpl implements TaskService {

    private Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;


    /**
     * 保存新的待办事项
     * @param task
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveNewTask(Task task) {
        task.setDone((byte)0); //未完成
        task.setCreateTime(new Date());

        taskMapper.insert(task);
        logger.info("创建新的待办事项 {}",task.getTitle());

        //添加新的调度任务
        if(StringUtils.isNotEmpty(task.getRemindTime())) {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.putAsString("accountId",task.getAccountId());
            jobDataMap.put("message",task.getTitle());

            JobDetail jobDetail = JobBuilder
                    .newJob(SendMessageJob.class)
                    .setJobData(jobDataMap)
                    .withIdentity(new JobKey("taskID:"+task.getId(),"sendMessageGroup"))
                    .build();

            //2017-09-08 12:35 -> cron
            //String -> DateTime
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
            DateTime dateTime = formatter.parseDateTime(task.getRemindTime());

            StringBuilder cron = new StringBuilder("0")
                    .append(" ")
                    .append(dateTime.getMinuteOfHour())
                    .append(" ")
                    .append(dateTime.getHourOfDay())
                    .append(" ")
                    .append(dateTime.getDayOfMonth())
                    .append(" ")
                    .append(dateTime.getMonthOfYear())
                    .append(" ? ")
                    .append(dateTime.getYear());

            logger.info("CRON EX: {}" ,cron.toString());

            ScheduleBuilder scheduleBuilder =
                    CronScheduleBuilder.cronSchedule(cron.toString()); //!!!! Cron表达式
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withSchedule(scheduleBuilder)
                    .build();

            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            try {
                scheduler.scheduleJob(jobDetail, trigger);
                scheduler.start();
            } catch (Exception ex) {
                throw new ServiceException(ex,"添加定时任务异常");
            }

        }
    }

    /**
     * 根据用户ID查找对应的待办事项列表
     *
     * @param id
     * @return
     */
    @Override
    public List<Task> findTaskByAccountId(Integer id) {
        TaskExample taskExample = new TaskExample();
        taskExample.createCriteria().andAccountIdEqualTo(id);
        taskExample.setOrderByClause("id desc");

        return taskMapper.selectByExample(taskExample);
    }

    /**
     * 根据ID查找待办事项
     *
     * @param id
     * @return
     */
    @Override
    public Task findTaskById(Integer id) {
        return taskMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据ID删除待办事项
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteById(Integer id) {
        Task task = findTaskById(id);
        taskMapper.deleteByPrimaryKey(id);
        //删除定时任务
        if(StringUtils.isNotEmpty(task.getRemindTime())) {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            try {
                scheduler.deleteJob(new JobKey("taskID:" + id, "sendMessageGroup"));
                logger.info("成功删除定时任务 ID:{} groupName:{}" ,id,"sendMessageGroup");
            } catch (Exception ex) {
                throw new ServiceException(ex,"删除定时任务异常");
            }
        }

    }
}
