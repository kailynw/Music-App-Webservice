package com.restendpoints.musicapp.service;

import com.restendpoints.musicapp.constants.Constants;
import com.restendpoints.musicapp.dto.user.Auth0UserDataDTO;
import com.restendpoints.musicapp.dto.user.UserRequestDTO;
import com.restendpoints.musicapp.dto.user.UserResponseDTO;
import com.restendpoints.musicapp.entity.User;
import com.restendpoints.musicapp.repository.UserRepository;
import com.restendpoints.musicapp.util.DTOUtil;
import com.restendpoints.musicapp.util.DateUtil;
import com.restendpoints.musicapp.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageRetrievalService imageRetrievalService;

    @Autowired
    AuthService authService;

    public List<User> getUsers(){
        List<User> userList = userRepository.findAll();
        return userList;
    }

    public User getUser(Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            log.warn("User does not exist..");
            return null;
        }else{
            log.info("User retrieved: {}", user);
            return user.get();
        }
    }

    public User createUser(UserRequestDTO userRequestDTO){
        User user = DTOUtil.toUser(userRequestDTO);
        String today = DateUtil.getTodaysDate();

        user.setCreatedDate(today);
        user.setNumberOfFollowers(Constants.ZERO);
        user.setNumberOfFollowing(Constants.ZERO);
        user.setProfilePictureUrl(imageRetrievalService.retrieveRandomImageUrl());
        User createdUser = this.userRepository.save(user);
        log.info("User created: {}", createdUser);
        return createdUser;
    }

    public User updateUser(UserRequestDTO userRequestDTO, Long userId) {
        User userUpdate = DTOUtil.toUser(userRequestDTO);
        userUpdate.setUserId(userId);

        Long potentialUserId = userUpdate.getUserId();
        Optional<User> potentialExistingUser = userRepository.findById(potentialUserId);
        boolean userExists = potentialExistingUser.isPresent();

        if (userExists) {
            User existingUser = potentialExistingUser.get();
            User updatedUser = this.handleUserUpdate(userUpdate, existingUser);
            return updatedUser;
        } else {
            log.error("User doesn't exist. Could not update");
            return null;
        }
    }

    private User handleUserUpdate(User userUpdate, User existingUser){
        String username = userUpdate.getUserName();
        String bio = userUpdate.getBio();
        String instagramUrl = userUpdate.getInstagramUrl();
        String profilePictureUrl = userUpdate.getProfilePictureUrl();

        if (ValidationUtil.isUpdatable(existingUser.getUserName(), username)){
            log.info("User id: {} | Updating user name: {} -> {}", existingUser.getUserId(), existingUser.getUserName(), username);
            existingUser.setUserName(username);
        }

        if (ValidationUtil.isUpdatable(existingUser.getBio(), bio)){
            log.info("User id: {} | Updating user bio: {} -> {}", existingUser.getUserId(), existingUser.getBio(), bio);
            existingUser.setBio(bio);
        }

        if (ValidationUtil.isUpdatable(existingUser.getInstagramUrl(), instagramUrl)){
            log.info("User id: {} | Updating user instagram url: {} -> {}", existingUser.getUserId(), existingUser.getInstagramUrl(), instagramUrl);
            existingUser.setInstagramUrl(instagramUrl);
        }

        if (ValidationUtil.isUpdatable(existingUser.getProfilePictureUrl(), profilePictureUrl)){
            log.info("User id: {} | Updating user profile picture url: {} -> {}", existingUser.getUserId(), existingUser.getProfilePictureUrl(), profilePictureUrl);
            existingUser.setProfilePictureUrl(profilePictureUrl);
        }

        User updatedUser = userRepository.save(existingUser);
        log.info("Updated user: {}", updatedUser);
        return updatedUser;
    }

    public void deleteUser(Long userId){
        Optional<User> potentialUser = userRepository.findById(userId);
        boolean userExists = potentialUser.isPresent();

        if(userExists){
            log.info("Deleting user: {}", userId);
            userRepository.deleteById(userId);
        }
    }

    public User loginUser(String bearerToken, Auth0UserDataDTO auth0UserDataDTO) throws Exception {
        //Validate token;
        String auth0UserId = authService.validateJwtToken(bearerToken);

        if(auth0UserId==null)
            throw new Exception("Unable to login user | invlaid auth0 token");

        User user = userRepository.findByAuth0UserId(auth0UserId);

        //User exists
        if(user!=null) {
            log.info("User retrieved: " + user);
            user.setNumberOfLogins(user.getNumberOfLogins()+1L);
            this.userRepository.save(user);
            return user;
        }
        else{
            UUID uuid = UUID.randomUUID();
            user = new User();
            String today = DateUtil.getTodaysDate();
            user.setUserName("user"+uuid);
            user.setProfilePictureUrl(auth0UserDataDTO.getProfilePictureUrl());
            user.setEmail(auth0UserDataDTO.getEmail());
            user.setCreatedDate(today);
            user.setNumberOfFollowers(Constants.ZERO);
            user.setNumberOfFollowing(Constants.ZERO);
            user.setProfilePictureUrl(auth0UserDataDTO.getProfilePictureUrl());
            user.setAuth0UserId(auth0UserId);
            user.setNumberOfLogins(1L);
            User createdUser = this.userRepository.save(user);
            log.info("User created: {}", createdUser);
            return user;
        }

    }
}
