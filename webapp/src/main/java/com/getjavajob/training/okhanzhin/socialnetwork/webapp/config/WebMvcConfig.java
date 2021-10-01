package com.getjavajob.training.okhanzhin.socialnetwork.webapp.config;

import com.getjavajob.training.okhanzhin.socialnetwork.service.config.ServiceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Import(ServiceConfig.class)
public class WebMvcConfig implements WebMvcConfigurer {
}
