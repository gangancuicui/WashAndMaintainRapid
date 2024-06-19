package com.washmaintance.config;
import com.washmaintance.utils.LocalTimeTypeHandler;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;

import java.time.LocalTime;
@org.springframework.context.annotation.Configuration
public class MyBatisConfig {
    @Bean
    public LocalTimeTypeHandler localTimeTypeHandler() {
        return new LocalTimeTypeHandler();
    }
}
