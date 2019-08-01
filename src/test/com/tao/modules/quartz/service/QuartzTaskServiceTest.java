package com.tao.modules.quartz.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QuartzTaskServiceTest {
    @Autowired
    private QuartzTaskService quartzTaskService;
    @Test
    public void scanTask() {
        quartzTaskService.scanTask();
    }

    @Test
    public void scanClass(){
        String clazz = "com.tao.modules.quartz.task.service.TaskPrintTimeService";
        quartzTaskService.scanClass(clazz);
    }
}