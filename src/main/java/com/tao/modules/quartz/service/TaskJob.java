package com.tao.modules.quartz.service;

import com.tao.modules.quartz.task.service.TaskPrintTimeService;
import com.tao.modules.quartz.task.serviceImpl.TaskPrintTimeServiceImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TaskJob implements Job{


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //构建Job
        TaskPrintTimeService taskPrintTimeService = new TaskPrintTimeServiceImpl();
        taskPrintTimeService.PrintMinute();
    }
}
