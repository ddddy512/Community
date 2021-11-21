package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
//@Scope("prototype")   //bean默认单例，想要每次getbean都会创建新的实例，加此注解
public class AlphaService {
    @Autowired
    private AlphaDao alphaDao;   //AlphaService依赖AlphaDao，将AlphaDao注入AlphaService

    public AlphaService(){
        System.out.println("实例化Alphaservice");
    }

    @PostConstruct     //初始化一般在构造之前调用，此时设置在构造器之后调用
    public void init(){
        System.out.println("初始化Alphaservice");
    }
    @PreDestroy   //在销毁对象之前调用
    public void destroy(){
        System.out.println("销毁Alphaservice");
    }

    public String find(){
        return alphaDao.select();
    }
}
