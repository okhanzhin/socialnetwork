package com.getjavajob.training.okhanzhin.socialnetwork.dao.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource(value = {"classpath:application-test.properties"})
@EnableTransactionManagement
//@ComponentScan("com.getjavajob.training.okhanzhin.socialnetwork.dao.hibernate")
//@EnableAutoConfiguration
@EnableJpaRepositories("com.getjavajob.training.okhanzhin.socialnetwork.dao.spring")
@EntityScan(basePackages = "com.getjavajob.training.okhanzhin.socialnetwork.domain")
public class DaoTestConfig {
}
