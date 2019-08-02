package com.tao.runner;

import com.tao.modules.quartz.service.QuartzTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 项目启动时自动执行
 */
@Component
public class MyRunner implements CommandLineRunner {
    @Autowired
    private QuartzTaskService quartzTaskService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("----------定时任务开始执行-----------");
        quartzTaskService.buildTask(quartzTaskService.scanTask());
    }
}
