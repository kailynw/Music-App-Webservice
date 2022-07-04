package com.restendpoints.musicapp.controller;


import com.restendpoints.musicapp.entity.User;
import com.restendpoints.musicapp.service.SongService;
import com.restendpoints.musicapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;


import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    SongService songService;

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/users", method = GET)
    public List<User> getUsers(){
        List<User> userList = userService.getUsers();
        return userList;
    }

    @RequestMapping(value = "/users/{userId}", method = GET)
    public User getUser(@PathVariable("userId") Long userId){
        User user = userService.getUser(userId);
        return user;
    }

    @RequestMapping(value = "/user", method = POST)
    public User createUser(@RequestBody User user){
        logger.info("User: {}", user.toString());
        User createdUser = userService.createUser(user);
        return createdUser;
    }

    @RequestMapping(value = "/user", method = PUT)
    public User updateUSer(@RequestBody User user){
        User updatedUser = userService.updateUser(user);
        return updatedUser;
    }
}
