package com.layton.demojwt.common.security.filter;

import com.layton.demojwt.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.security.auth.message.AuthException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 用户授权
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter{
    private static final Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        String header = req.getHeader(JwtUtils.TOKEN_HEADER);

        if (header == null || !header.startsWith(JwtUtils.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(req, res);
    }

    /**
     * token 验证
     * @param request
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String headerToken = request.getHeader(JwtUtils.TOKEN_HEADER);

        // 请求中没有token，返回null
        if (headerToken == null) {
            return null;
        }

        // 验证token，失败返回null
        try {
            String username = JwtUtils.verifyToken(headerToken.replace(JwtUtils.TOKEN_PREFIX, ""));
            return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
        } catch(AuthException e) {
            return null;
        }

    }
}
