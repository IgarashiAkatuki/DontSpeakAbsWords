package com.project.config;

import com.project.filter.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@ConditionalOnProperty(
        name = "config.enableSecurity",
        havingValue = "true"
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthorizationFilter filter;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());

    }
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/swagger-ui/**").hasAuthority("admin")
                .antMatchers("/swagger").hasAuthority("NULL")
                .antMatchers("/api/admin/**").hasAnyAuthority("admin")
                .antMatchers("/admin/login").permitAll()
                .antMatchers("/admin.html").hasAuthority("admin")
                .antMatchers("/*.html","/js/*.js,","/*.js").permitAll()
                .antMatchers("/admin.html").hasAuthority("admin")
                .antMatchers("/admin/**").access("hasAnyAuthority('admin')");
//        http.formLogin()
//                .loginProcessingUrl("/admin/form")
//                .loginPage("/admin/login")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .defaultSuccessUrl("/admin.html")
//                .failureForwardUrl("/admin/login");

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        http.logout().logoutSuccessUrl("/").deleteCookies();
        http.csrf().disable();
    }

}
