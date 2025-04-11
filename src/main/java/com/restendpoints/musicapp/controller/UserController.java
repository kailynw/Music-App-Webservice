package com.restendpoints.musicapp.controller;

import com.restendpoints.musicapp.dto.user.Auth0UserDataDTO;
import com.restendpoints.musicapp.dto.user.UserRequestDTO;
import com.restendpoints.musicapp.dto.user.UserResponseDTO;
import com.restendpoints.musicapp.entity.User;
import com.restendpoints.musicapp.service.UserService;
import com.restendpoints.musicapp.util.AuthUtil;
import com.restendpoints.musicapp.util.DTOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Slf4j
public class UserController {


    @Autowired
    UserService userService;

    @RequestMapping(value = "/users", method = GET)
    public ResponseEntity<List<UserResponseDTO>> getUsers(){
        List<User> userList = userService.getUsers();
        List<UserResponseDTO> userResponseDTOList = DTOUtil.toUserResponseDTOList(userList);
        return ResponseEntity.ok(userResponseDTOList);
    }

    @RequestMapping(value = "/users/{userId}", method = GET)
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long userId){
        User user = userService.getUser(userId);
        UserResponseDTO userResponseDTO = DTOUtil.toUserResponseDTO(user);
        return ResponseEntity.ok(userResponseDTO);
    }

    @RequestMapping(value = "/user", method = POST)
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO){
        log.info("User Request: {}", userRequestDTO.toString());
        User user = userService.createUser(userRequestDTO);
        UserResponseDTO userResponseDTO = DTOUtil.toUserResponseDTO(user);
        return ResponseEntity.ok(userResponseDTO);
    }

    @RequestMapping(value = "/user/{userId}", method = PATCH)
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long userId, @RequestBody UserRequestDTO userRequestDTO){
        User user = userService.updateUser(userRequestDTO, userId);
        UserResponseDTO userResponseDTO = DTOUtil.toUserResponseDTO(user);
        return ResponseEntity.ok(userResponseDTO);
    }

    @RequestMapping(value = "/user/edit/{userId}", method = POST)
    public ResponseEntity<UserResponseDTO> updateUserInfo(@PathVariable Long userId, @RequestBody UserRequestDTO userRequestDTO){
        User user = userService.updateUser(userRequestDTO, userId);
        UserResponseDTO userResponseDTO = DTOUtil.toUserResponseDTO(user);
        return ResponseEntity.ok(userResponseDTO);
    }


    @RequestMapping(value = "/user/{userId}", method = DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok("Successful");
    }

    @RequestMapping(value = "/user/auth/login", method = POST)
    public ResponseEntity<UserResponseDTO> loginUser(@RequestBody Auth0UserDataDTO auth0UserDataDTO, @RequestHeader HttpHeaders httpHeaders) throws Exception {
        log.info(httpHeaders.toString());
        log.info(auth0UserDataDTO.toString());
        String bearerToken = AuthUtil.extractBearerTokenFromHttpHeaders(httpHeaders);
        User user = userService.loginUser(bearerToken, auth0UserDataDTO);
        UserResponseDTO userResponseDTO = DTOUtil.toUserResponseDTO(user);
        return ResponseEntity.ok(userResponseDTO);
    }

}
