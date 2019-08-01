package com.tao.modules.quartz.service;

import com.tao.modules.common.ScanPackageUtil;
import com.tao.modules.common.annotation.TaoTask;
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

    /**
     * 扫描task包下所有带有@TaoTask标注的方法
     * 规定：任务类必须以Task开头，以Service结尾
     * @return
     */
    public String scanTask(){
        String packageName = "com.tao.modules.quartz.task.service";
        List<String> classList = ScanPackageUtil.scanClass(packageName);
        for(String className:classList){
            String[]  classSplit= className.split(".");
            String clazz = classSplit[classSplit.length-1];

            //正则匹配
            String patternString = "Task.*Service";
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(clazz);
            if(matcher.find()){

            }

        }
        return null;
    }


    /**
     * 获取一个类下的所有@TaoTask注解方法的信息
     * @param clazz
     * @return
     */
    public List<Map> scanClass(String clazz){
        try {
            List<Map> result = new ArrayList<>();
            Class<?> aClass = Class.forName(clazz);
            Method[] methods = aClass.getMethods();
            for (Method method:methods){
                TaoTask annotation = method.getAnnotation(TaoTask.class);
                if(annotation!=null){
                    Map<String,String> tempMap = new HashMap<>();
                    tempMap.put("name",annotation.name());
                    //TODO other args
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
