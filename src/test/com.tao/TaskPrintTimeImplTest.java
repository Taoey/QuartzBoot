package com.tao;

import com.tao.modules.quartz.task.service.TaskPrintTimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TaskPrintTimeImplTest {

    @Autowired
    private TaskPrintTimeService taskPrintTime;
    @Test
    public void printMinute() {
        taskPrintTime.PrintMinute();
    }
}