package com.tao.modules.quartz.service;

import com.tao.modules.common.ScanPackageUtil;
import com.tao.modules.common.annotation.TaoTask;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 定时任务业务管理类
 */
@Service
public class QuartzTaskService {

    @Autowired
    private Scheduler scheduler;
    /**
     * 扫描task包下所有带有@TaoTask标注的方法
     * 规定：任务类必须以Task开头，以Service结尾
     * @return
     */
    public List<Map> scanTask(){
        List<Map> result = new ArrayList<>();
        String packageName = "com.tao.modules.quartz.task.service";
        List<String> classList = ScanPackageUtil.scanClass(packageName);
        for(String className:classList){
            String[]  classSplit= className.split("\\.");
            String clazz = classSplit[classSplit.length-1];

            //正则匹配
            String patternString = "Task.*Service";
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(clazz);
            if(matcher.find()){
                List<Map> maps = this.scanClass(className);
                result.addAll(maps);
            }
        }
        return result;
    }


    /**
     * 获取一个类下的所有@TaoTask注解方法的信息
     * @param clazz
     * @return
     */
    public List<Map> scanClass(String clazz){
        List<Map> result = new ArrayList<>();
        try {
            Class<?> aClass = Class.forName(clazz);
            Method[] methods = aClass.getMethods();
            for (Method method:methods){
                TaoTask annotation = method.getAnnotation(TaoTask.class);
                if(annotation!=null){
                    Map<String,String> tempMap = new HashMap<>();
                    tempMap.put("name",annotation.name());
                    //TODO other args
                    result.add(tempMap);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 构建定时任务并执行
     * @param taskInfoList
     * @return
     */
    public String buildTask(List<Map> taskInfoList){
        try {
            JobDetail jobDetail = JobBuilder.newJob(TaskJob.class)
                    .withIdentity("PrintJob", "group1")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "triggerGroup1")
                    .startNow()//立即生效
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(1)//每隔1s执行一次
                            .repeatForever()).build();//一直执行

            scheduler.scheduleJob(jobDetail, trigger);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
