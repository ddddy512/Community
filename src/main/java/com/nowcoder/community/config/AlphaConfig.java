package com.nowcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration    //标识配置类
public class AlphaConfig {
    @Bean  //定义一个第三方bean
    //方法返回的对象bean将被装配到容器中
    public SimpleDateFormat simpleDateFormat(){  //方法名simpleDateFormat=Bean名
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //new实例化

    }
}
