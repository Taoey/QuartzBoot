package com.tao.modules.quartz.task.serviceImpl;

import com.tao.modules.common.TimeUtil;
import com.tao.modules.quartz.task.service.TaskPrintTimeService;
import org.springframework.stereotype.Service;

@Service
public class TaskPrintTimeServiceImpl implements TaskPrintTimeService {
    @Override
    public String PrintMinute() {
        String date = TimeUtil.timeStamp2Date(String.valueOf(System.currentTimeMillis() / 1000));
        System.out.println("TaskPrintTime 打印时间："+date);
        return "succeed";
    }
}
