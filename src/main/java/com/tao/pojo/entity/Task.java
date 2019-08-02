package com.tao.pojo.entity;

import lombok.Data;

@Data
public class Task {
    private String name;
    private String bean;
    private String method;
    private String defaultCron;
}
