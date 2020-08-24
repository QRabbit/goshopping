package com.qrabbit.youpinghui;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.qrabbit.youpinghui.mapper")
public class Appmanager {
    public static void main(String[] args) {
        SpringApplication.run(Appmanager.class,args);
    }
}
