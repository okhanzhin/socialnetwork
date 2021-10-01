package com.getjavajob.training.okhanzhin.socialnetwork.service.config;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.config.DaoConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableScheduling
@Import(DaoConfig.class)
public class ServiceConfig {
}
