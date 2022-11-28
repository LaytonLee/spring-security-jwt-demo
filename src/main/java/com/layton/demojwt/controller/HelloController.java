package com.layton.demojwt.controller;

import com.layton.demojwt.entity.User;
import com.layton.demojwt.service.SysUserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private SysUserServiceImpl userService;

    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/hello")
    public String hello() {
        return "hello jwt !";
    }
    @GetMapping("/admin")
    public String admin() {
        return "hello admin !";
    }

    @PostMapping("/add")
    public String addUser(@RequestBody User user) {
//        BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();
        logger.info("add user...." + user.toString());
//        User user = new User();
//        user.setUserName(username);
//        user.setPassword(cryptPasswordEncoder.encode(password));

//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userService.addUser(user);
        return "ok";
    }

    @PostMapping("/get")
    public User findUserByUsername(@RequestBody User userParam) {
        System.out.println("username: " + userParam.getUsername());
        User user = userService.loadUserByUsername(userParam.getUsername());
        return user;
    }
}
