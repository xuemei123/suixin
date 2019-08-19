package com.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication        //标识主启动类
@MapperScan("com.jt.mapper")  //将mybatis的mapper接口交给spring管理
public class SpringBootRun {
     public static void main(String[] args){
		SpringApplication.run(SpringBootRun.class, args);
	}
}
