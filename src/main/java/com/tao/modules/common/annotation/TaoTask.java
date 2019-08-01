package com.tao.modules.common.annotation;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TaoTask {
    String name();                   //任务功能简介

    String explain() default  "";    //任务功能详解

    String author() default "";      //作者

    String date() default "";        //建立日期

    String corn() default "0 0 0 0 0 ? 2030-";  //默认cron表达式
}
