package com.metain.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class MetainProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetainProjectApplication.class, args);
    }

}
