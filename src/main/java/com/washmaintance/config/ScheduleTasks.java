package com.washmaintance.config;


import com.washmaintance.service.WashOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduleTasks {
    @Autowired private WashOrderService washOrderService;
    @Scheduled(fixedRate = 1440000)
    void cancelOverdueOrder(){
        washOrderService.cancelOverdueOrder();
    }
} 