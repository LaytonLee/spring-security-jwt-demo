package com.layton.demojwt.common.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.layton.demojwt.common.R;
import com.layton.demojwt.common.ResponseEnum;
import com.layton.demojwt.entity.User;
import com.layton.demojwt.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * 用户身份验证
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        // 设置用于登录验证的url，默认 /login
        setFilterProcessesUrl("/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        // 必须使用post方式提交用户名密码
        if (!req.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + req.getMethod());
        }

        try {
            // 获取登录的用户信息
            User user = new ObjectMapper()
                    .readValue(req.getInputStream(), User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            logger.info("读取登录用户失败");
            throw new AuthenticationServiceException("读取登录用户失败");
        }
    }

    /**
     * 用户名密码验证成功，返回token
     * @param req
     * @param res
     * @param chain
     * @param authResult
     * @throws IOException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authResult)
        throws IOException {

        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        StringBuffer as = new StringBuffer();
        for (GrantedAuthority authority : authorities) {
            as.append(authority.getAuthority())
                    .append(",");
        }

        String token = JwtUtils.genToken(authResult.getName(), as.toString(), false);

        res.setContentType("application/json;charset=utf-8");

        Map<String, String> tokenObj = new HashMap<>();
        tokenObj.put("token", JwtUtils.TOKEN_PREFIX  + token);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(res.getWriter(), R.success(tokenObj));
    }

    /**
     * 用户名密码验证失败，返回验证失败信息
     * @param req
     * @param res
     * @param failed
     * @throws IOException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException failed)
            throws IOException {

        res.setContentType("application/json;charset=utf-8");

        R responseBody = R.fail(ResponseEnum.AUTH_UNAUTHORIZED);

        if (failed instanceof AccountExpiredException) {
            responseBody = R.fail(ResponseEnum.AUTH_TOKEN_EXPIRED);
        } else if (failed instanceof BadCredentialsException) {
            responseBody = R.fail(ResponseEnum.AUTH_TOKEN_VERIFY_FAILED);
        } else if (failed instanceof AuthenticationServiceException) {
            responseBody = R.fail(ResponseEnum.AUTH_UNAUTHORIZED.getCode(), failed.getMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(res.getWriter(), responseBody);
    }
}
