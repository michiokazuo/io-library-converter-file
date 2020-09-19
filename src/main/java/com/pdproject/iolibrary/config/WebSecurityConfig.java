package com.pdproject.iolibrary.config;

import com.pdproject.iolibrary.service_impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        // cho phép tất cả các request truy cập
        http.authorizeRequests().antMatchers("/", "/home", "/login", "/logout", "/403").permitAll();

        // chỉ cho phép người dùng đã đăng nhập với quền user hoặc admin truy cập
        http.authorizeRequests().antMatchers("/user/**")
                .access("hasAnyRole('ROLE_USER','ROLE_ADMIN')");

        // chỉ cho phép người dùng đã đăng nhập với admin truy cập chỉ cho
        http.authorizeRequests().antMatchers("/admin/**","/api/v1/private/**")
                .access("hasRole('ROLE_ADMIN')");

        // khi người dùng login với vai trò X, nhưng truy cập vào trang yêu cầu vai trò Y
        // ngoại lệ AccessDeniedException sẽ ném ra
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

        // cấu hình cho form login
        http.authorizeRequests().and().formLogin()
                .loginProcessingUrl("/security-login")
                .loginPage("/login")
                .defaultSuccessUrl("/api/v1/public/user/login-success")
                .failureUrl("/api/v1/public/user/login-fail")
                .usernameParameter("username")
                .passwordParameter("password")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/home");

        // cấu hình remember me, thời gian 1296000 giây
        http.rememberMe().key("iolibrary").tokenValiditySeconds(1296000);
    }
}
