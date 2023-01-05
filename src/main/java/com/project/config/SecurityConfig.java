package com.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@ConditionalOnProperty(
        name = "config.enableSecurity",
        havingValue = "true"
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    UserDetailsService userDetailsService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/swagger-ui/**").hasAuthority("admin")
                .antMatchers("/swagger").hasAuthority("NULL")
                .antMatchers("/api/admin/**").hasAnyAuthority("admin")
                .antMatchers("/admin/login").permitAll()
                .antMatchers("/admin/**").access("hasAnyAuthority('admin')");
        http.formLogin()
                .loginProcessingUrl("/admin/form")
                .loginPage("/admin/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/admin/info")
                .failureForwardUrl("/admin/login");

        http.logout().logoutSuccessUrl("/").deleteCookies();
        http.csrf().disable();
    }

}
