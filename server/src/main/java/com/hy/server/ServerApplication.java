package com.hy.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@ComponentScan(basePackages = {"com.hy"})
@SpringBootApplication(scanBasePackages = {"com.hy.server","com.hy.business"})
@MapperScan("com.hy.business.dao")
public class ServerApplication {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {

        SpringApplication.run(ServerApplication.class, args);
    }

}
