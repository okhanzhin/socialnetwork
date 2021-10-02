 package com.getjavajob.training.okhanzhin.socialnetwork.webapp.config;

 import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
public class TomcatConfig {
    @Value("${spring.datasource.jndi-name}")
    String jndiName;
    @Value("${spring.datasource.tomcat.data-source-j-n-d-i}")
    String resourceName;
    @Value("${spring.datasource.tomcat.driver-class-name}")
    String driverClassName;
    @Value("${spring.datasource.tomcat.url}")
    String url;
    @Value("${spring.datasource.tomcat.username}")
    String username;
    @Value("${spring.datasource.tomcat.password}")
    String password;
    @Value("${spring.datasource.tomcat.min-idle}")
    String minIdle;
    @Value("${spring.datasource.tomcat.max-idle}")
    String maxIdle;
    @Value("${spring.datasource.tomcat.max-wait}")
    String maxWaitMillis;

//    @Bean
//    public TomcatServletWebServerFactory tomcatFactory() {
//        return new TomcatServletWebServerFactory() {
//
//            @Override
//            protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(
//                    Tomcat tomcat) {
//                tomcat.enableNaming();
//                return super.getTomcatEmbeddedServletContainer(tomcat);
//            }
//
//            @Override
//            protected void postProcessContext(Context context) {
//                ContextResource resource = new ContextResource();
//                resource.setName("jdbc/myDataSource");
//                resource.setType(DataSource.class.getName());
//                resource.setProperty("driverClassName", "your.db.Driver");
//                resource.setProperty("url", "jdbc:yourDb");
//
//                context.getNamingResources().addResource(resource);
//            }
//        };
//    }

    @Bean
    public TomcatServletWebServerFactory tomcatFactory() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
                tomcat.enableNaming();
//                tomcat.setBaseDir("/home/erixon/misc/git/oleg-socialnetwork/webapp/src/main/webapp");
                return super.getTomcatWebServer(tomcat);
            }

            @Override
            protected void postProcessContext(Context context) {
                ContextResource resource = new ContextResource();
                resource.setName(resourceName);
                resource.setType(DataSource.class.getName());
                resource.setProperty("factory", "org.apache.tomcat.jdbc.pool.DataSourceFactory");
                resource.setProperty("driverClassName", driverClassName);
                resource.setProperty("url", url);
                resource.setProperty("username", username);
                resource.setProperty("password", password);
//                resource.setProperty("minIdle", minIdle);
//                resource.setProperty("maxIdle", maxIdle);
//                resource.setProperty("maxWaitMillis", maxWaitMillis);
                context.getNamingResources().addResource(resource);
            }
        };
    }

    @Bean(destroyMethod="")
    public DataSource jndiDataSource() throws IllegalArgumentException, NamingException {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName(jndiName);
        bean.setProxyInterface(DataSource.class);
        bean.setLookupOnStartup(false);
        bean.afterPropertiesSet();
        return (DataSource)bean.getObject();
    }
}
