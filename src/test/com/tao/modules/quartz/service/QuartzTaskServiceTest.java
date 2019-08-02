package com.tao.modules.quartz.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

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
        List<Map> maps = quartzTaskService.scanClass(clazz);
        System.out.println(maps);
    }

    @Test
    public void buildTask(){
        quartzTaskService.buildTask(null);
    }
}