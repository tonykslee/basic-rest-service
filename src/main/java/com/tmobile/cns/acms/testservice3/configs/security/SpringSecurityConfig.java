package com.tmobile.cns.acms.testservice3.configs.security;

import com.tmobile.cns.acms.testservice3.configs.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(101)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private static String REALM = "test_service_3";

    @Autowired
    private ApplicationProperties constants;
    @Autowired
    private AuthenticationEntryPoint authEntryPoint;
    @Value("${spring.security.user.name}")
    private String username;
    @Value("${spring.security.user.password}")
    private String password;

    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//                .antMatchers("/swagger-ui.html");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/testWithoutAuth").permitAll()
                .antMatchers("/external/api").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic().realmName(REALM)
                .authenticationEntryPoint(authEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(username)
                .password(passwordEncoder().encode(password))
                .roles("USER").roles("ACTUATOR");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}