package com.layton.demojwt.service;

import com.layton.demojwt.entity.User;
import com.layton.demojwt.mapper.UserMapper;
import com.layton.demojwt.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class SysUserServiceImpl implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findUserByUsername(username);

        return user;
    }

    /**
     * 新增用户
     * @param user
     */
    public void addUser(User user) {

        // 加密密码
        user.setPassword(JwtUtils.getPasswordEncoder().encode(user.getPassword()));

        Integer result = userMapper.addUser(user);

    }
}
