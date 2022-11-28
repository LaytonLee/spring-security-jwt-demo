package com.layton.demojwt.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.security.auth.message.AuthException;
import java.io.IOException;
import java.security.KeyPair;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JwtUtils {
    // logger
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    // token请求头标识
    public static final String TOKEN_HEADER = "Authorization";

    // token 前缀
    public static final String TOKEN_PREFIX = "Bearer ";

    // 默认密钥
    private static final String DEFAULT_SECRET = "myscrettestmyscrettestmyscrettestmyscrettestmyscrettest";

    // 用户身份
    private final static String ROLE_CLAIM = "roles";

    // token有效期, 默认15分钟 （单位分钟）
    private final static long EXPIRE_TIME = 15 * 60 * 1000;

    // 设置Remember-me功能后的token有效期
    private final static long EXPIRE_TIME_REMEMBER = 7 * 24 * 60 * 60 * 1000;

    /**
     * 使用 jjwt 生成 token
     * @param username
     * @param roles
     * @param rememberMe
     * @return
     */
    public static String genToken(String username, String roles, boolean rememberMe) {

        // token 过期时间
        Date expireDate = rememberMe ? new Date(System.currentTimeMillis() + EXPIRE_TIME_REMEMBER) : new Date(System.currentTimeMillis() + EXPIRE_TIME);

//        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);

        return Jwts.builder()
                .setSubject(username)
                .claim(ROLE_CLAIM, roles.toString())
                .setExpiration( expireDate )
                .signWith( SignatureAlgorithm.HS256, DEFAULT_SECRET )
//                .signWith(keyPair.getPrivate())     // 非对称加密
                .compact();

    }

    /**
     * 使用 jjwt 验证 token
     * @param token
     * @throws AuthException
     */
    public static String verifyToken(String token) throws AuthException {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(DEFAULT_SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            logger.info("token expired");
            throw new AccountExpiredException(e.getMessage());
        } catch (SignatureException e) {
            logger.info("token verify failed");
            throw new BadCredentialsException(e.getMessage());
        }
    }

    /**
     * 获取密码加密方式
     * @return
     */
    public static PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
