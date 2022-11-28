package com.layton.demojwt.config;

import com.layton.demojwt.service.SysUserServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

//@Configuration
//@EnableWebSecurity
// extends WebSecurityConfigurerAdapter
public class WebSecurityConfig1 {

    private SysUserServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 依赖注入
     * @param userService
     */
    public WebSecurityConfig1(SysUserServiceImpl userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and().authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/user/login").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
//                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
//                .csrf().disable()  // spring security默认启用csrf，会拦截所有跨域的post请求
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  //this disables session creation on Spring Security
//    }

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
//    }

//    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
