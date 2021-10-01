package com.getjavajob.training.okhanzhin.socialnetwork.webapp;

import com.getjavajob.training.okhanzhin.socialnetwork.service.config.JmsConfig;
import com.getjavajob.training.okhanzhin.socialnetwork.webapp.config.TomcatConfig;
import com.getjavajob.training.okhanzhin.socialnetwork.webapp.config.WebMvcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.getjavajob.training.okhanzhin.socialnetwork"},
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASPECTJ,
                pattern = "com.getjavajob.training.okhanzhin.socialnetwork.dao.hibernate.*"))
@EnableAutoConfiguration
@Import({WebMvcConfig.class, JmsConfig.class, TomcatConfig.class})
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }
}