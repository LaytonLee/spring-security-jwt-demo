package com.layton.demojwt.common.security.config;

import com.layton.demojwt.common.security.filter.JWTAuthenticationFilter;
import com.layton.demojwt.common.security.filter.JWTAuthorizationFilter;
import com.layton.demojwt.service.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    /**
     * 通过bean注入 security filter chain
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // spring security默认启用csrf，会拦截所有跨域的post请求，这里使用token进行验证，禁用csrf
                .csrf().disable()
                // 基于token，禁用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests((authz) -> authz
                        // 放行登录接口
                        .antMatchers("/user/login").permitAll()
                        //.antMatchers().permitAll()
                        // 其他请求一律进行验证
                        .anyRequest().authenticated()
                )
                // 用户名密码验证
                .addFilter(new JWTAuthenticationFilter(authenticationManager))
                // token 验证
                .addFilter(new JWTAuthorizationFilter(authenticationManager))
                .exceptionHandling()
                .and()
                .userDetailsService(new SysUserServiceImpl())
                .build();
    }

    /**
     * 获取认证管理器，登录时使用
     * @param authenticationConfiguration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 密码加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
