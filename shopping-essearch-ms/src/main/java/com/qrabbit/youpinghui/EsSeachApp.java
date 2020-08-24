package com.qrabbit.youpinghui;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.qrabbit.youpinghui.mapper")
public class EsSeachApp {
    public static void main(String[] args) {
        SpringApplication.run(EsSeachApp.class,args);
    }
}
