package com.metain.web.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = {"com.metain.web.mapper"}) //mybatis mapper 경로
public class WebConfig {
}
