package com.getjavajob.training.okhanzhin.socialnetwork.webapp.config;

import com.getjavajob.training.okhanzhin.socialnetwork.webapp.filter.AccountAvailableValidationFilter;
import com.getjavajob.training.okhanzhin.socialnetwork.webapp.filter.AnonymousFilter;
import com.getjavajob.training.okhanzhin.socialnetwork.webapp.security.AccessDeniedHandlerImpl;
import com.getjavajob.training.okhanzhin.socialnetwork.webapp.security.AuthenticationFailureHandlerImpl;
import com.getjavajob.training.okhanzhin.socialnetwork.webapp.security.AuthenticationSuccessHandlerImpl;
import com.getjavajob.training.okhanzhin.socialnetwork.webapp.security.LogoutSuccessHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final int COOKIE_AGE_IN_DAYS = 7;

    private final UserDetailsService userDetailsService;
    private final AuthenticationSuccessHandlerImpl authenticationSuccessHandler;
    private final AuthenticationFailureHandlerImpl authenticationFailureHandler;
    private final LogoutSuccessHandlerImpl logoutSuccessHandler;
    private final AccessDeniedHandlerImpl accessDeniedHandler;
    private final AccountAvailableValidationFilter accountAvailableValidationFilter;

    public SecurityConfig(UserDetailsService userDetailsService,
                          AuthenticationSuccessHandlerImpl authenticationSuccessHandler,
                          AuthenticationFailureHandlerImpl authenticationFailureHandler,
                          LogoutSuccessHandlerImpl logoutSuccessHandler,
                          AccessDeniedHandlerImpl accessDeniedHandler,
                          AccountAvailableValidationFilter accountAvailableValidationFilter) {
        this.userDetailsService = userDetailsService;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.accessDeniedHandler = accessDeniedHandler;
        this.accountAvailableValidationFilter = accountAvailableValidationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.formLogin().loginPage("/login").usernameParameter(EMAIL)
            .successHandler(authenticationSuccessHandler)
            .failureHandler(authenticationFailureHandler).permitAll()
            .and()
            .authorizeRequests()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/account/new", "/account/isEmailExist", "/").anonymous()
            .antMatchers("/resources/**", "/templates/error", "/").permitAll()
            .anyRequest().authenticated()
            .and()
            .logout().invalidateHttpSession(true).clearAuthentication(true).deleteCookies("JSESSIONID")
            .logoutSuccessHandler(logoutSuccessHandler)
            .and()
            .rememberMe()
            .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(COOKIE_AGE_IN_DAYS))
            .key(PASSWORD)
            .userDetailsService(userDetailsService).authenticationSuccessHandler(authenticationSuccessHandler)
            .and()
            .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
            .and()
            .addFilterBefore(new AnonymousFilter(), DefaultLoginPageGeneratingFilter.class)
            .addFilterBefore(accountAvailableValidationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/scripts/**", "/img/**", "/webjars/**", "/ws/**");
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
