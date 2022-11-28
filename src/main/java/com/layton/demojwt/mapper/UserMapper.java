package com.layton.demojwt.mapper;

import com.layton.demojwt.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询用户
     * @return
     */
    User findUserByUsername(String username);

    /**
     * 添加用户
     * @param user
     * @return
     */
    Integer addUser(User user);


}
