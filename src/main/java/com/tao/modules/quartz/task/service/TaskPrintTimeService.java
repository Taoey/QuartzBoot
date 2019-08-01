package com.tao.modules.quartz.task.service;

import com.tao.modules.common.annotation.TaoTask;

public interface TaskPrintTimeService {

    @TaoTask(
            name="每隔2分钟打印时间",
            explain = "每隔2分钟打印时间，时间格式为：yyyy-MM-dd HH:mm:ss",
            author = "Tao",
            date = "2019-08-01",
            corn = "0 0/2 * * * ? "
    )
    String PrintMinute();
}
