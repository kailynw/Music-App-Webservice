package com.restendpoints.musicapp.controller;


import com.restendpoints.musicapp.dto.user.UserRequestDTO;
import com.restendpoints.musicapp.dto.user.UserResponseDTO;
import com.restendpoints.musicapp.entity.User;
import com.restendpoints.musicapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/users", method = GET)
    public ResponseEntity<List<UserResponseDTO>> getUsers(){
        List<UserResponseDTO> userResponseDTOList = userService.getUsers();
        return ResponseEntity.ok(userResponseDTOList);
    }

    @RequestMapping(value = "/users/{userId}", method = GET)
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long userId){
        UserResponseDTO userResponseDTO = userService.getUser(userId);
        return ResponseEntity.ok(userResponseDTO);
    }

    @RequestMapping(value = "/user", method = POST)
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO){
        logger.info("User Request: {}", userRequestDTO.toString());
        UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    @RequestMapping(value = "/user/{userId}", method = PATCH)
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long userId, @RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO userResponseDTO = userService.updateUser(userRequestDTO, userId);
        return ResponseEntity.ok(userResponseDTO);
    }
}
