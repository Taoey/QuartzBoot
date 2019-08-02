package com.tao.modules.quartz.service;

import com.tao.modules.common.ScanPackageUtil;
import com.tao.modules.common.annotation.TaoTask;
import com.tao.pojo.entity.Task;
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
    public List<Task> scanTask(){
        List<Task> result = new ArrayList<>();
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
                List<Task> tasks = this.scanClass(className);
                result.addAll(tasks);
            }
        }
        return result;
    }


    /**
     * 获取一个类下的所有@TaoTask注解方法的信息
     * @param clazz
     * @return
     */
    public List<Task> scanClass(String clazz){
        List<Task> result = new ArrayList<>();
        try {
            Class<?> aClass = Class.forName(clazz);
            Method[] methods = aClass.getMethods();
            for (Method method:methods){
                TaoTask annotation = method.getAnnotation(TaoTask.class);
                if(annotation!=null){
                    Task tempTask = new Task();
                    tempTask.setName(annotation.name());
                    tempTask.setBean(clazz);
                    tempTask.setMethod(method.getName());
                    tempTask.setDefaultCron(annotation.corn());

                    result.add(tempTask);
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
    public String buildTask(List<Task> taskInfoList){

        for(int i=0;i<taskInfoList.size();i++){
            Task taskInfo = taskInfoList.get(i);
            //开始构建一个job并进行触发
            try {
                JobDetail jobDetail = JobBuilder.newJob(TaskJob.class)
                        .withIdentity(taskInfo.getBean()+"-"+taskInfo.getMethod()+"Job", taskInfo.getBean())
                        .build();
                Map<String,String> taskMap = new HashMap<>();
                taskMap.put("name",taskInfo.getName());
                taskMap.put("bean",taskInfo.getBean());
                taskMap.put("method",taskInfo.getMethod());

                jobDetail.getJobDataMap().put("task",taskMap);

                //构建触发器：name为"bean-method",group为method
                Trigger trigger = TriggerBuilder.newTrigger().withIdentity(taskInfo.getBean()+"-"+taskInfo.getMethod(),
                        taskInfo.getBean())
                        .startNow()//立即生效
                        .withSchedule(CronScheduleBuilder.cronSchedule(taskInfo.getDefaultCron()))
                        .build();

                scheduler.scheduleJob(jobDetail, trigger);

            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
        return "OK";
    }
}
