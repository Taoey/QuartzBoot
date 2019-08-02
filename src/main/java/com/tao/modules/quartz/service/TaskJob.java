package com.tao.modules.quartz.service;

import com.tao.modules.common.ApplicationContextUtil;
import com.tao.modules.quartz.task.service.TaskPrintTimeService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

public class TaskJob implements Job{


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //打印日志
//        Map<String, Object> task = (Map<String, Object>)jobExecutionContext.getJobDetail().getJobDataMap().get("task");
//        String startLog = String.format("TaoQuartzJob---start---%s[%s-%s]",task.get("bean"), task.get("method"),task.get("name"));
//        System.out.println(startLog);

        //构造job
        ApplicationContext appContext = ApplicationContextUtil.getAppContext();
        TaskPrintTimeService taskPrintTimeService = (TaskPrintTimeService)ApplicationContextUtil.getBean("taskPrintTimeServiceImpl");
        //构建Job
        taskPrintTimeService.PrintMinute();

//        String endLog = String.format("TaoQuartzJob---end---%s[%s-%s]",task.get("bean"), task.get("method"),task.get("name"));
//        System.out.println(endLog);

    }
}
