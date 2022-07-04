package com.restendpoints.musicapp.service;

import com.restendpoints.musicapp.constants.Constants;
import com.restendpoints.musicapp.entity.User;
import com.restendpoints.musicapp.repository.UserRepository;
import com.restendpoints.musicapp.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    public static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    public List<User> getUsers(){
        List<User> userList = userRepository.findAll();
        return userList;
    }

    public User getUser(Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            logger.warn("User does not exist..");
            return null;
        }else{
            logger.info("User retrieved: {}", user);
            return user.get();
        }
    }

    public User createUser(User user){
        String today = DateUtil.getTodayDate();

        user.setCreatedDate(today);
        user.setNumberOfFollowers(Constants.ZERO);
        user.setNumberOfFollowing(Constants.ZERO);

        User createdUser = this.userRepository.save(user);
        logger.info("User created: {}", createdUser);
        return createdUser;
    }

    public User updateUser(User user) {
        Long userId = user.getUserId();
        Optional<User> potentialExistingUser = userRepository.findById(userId);
        boolean userExists = potentialExistingUser.isPresent();

        if (userExists) {
            User existingUser = potentialExistingUser.get();
            User updatedUser = this.handleUserUpdate(user, existingUser);
            return updatedUser;
        } else {
            logger.error("User doesn't exist. Could not update");
            return null;
        }
    }

    private User handleUserUpdate(User potentialUser, User existingUser){
        String username = potentialUser.getUserName();
        String bio = potentialUser.getBio();

        if (!StringUtils.isEmpty(username)){
            logger.info("User id: {} | Updating user name: {} -> {}", existingUser.getUserId(), existingUser.getUserName(), username);
            existingUser.setUserName(username);
        }

        if (!StringUtils.isEmpty(bio)){
            logger.info("User id: {} | Updating user bio: {} -> {}", existingUser.getUserId(), existingUser.getBio(), bio);
            existingUser.setBio(bio);
        }

        User updatedSong = userRepository.save(existingUser);
        logger.info("Updated user: {}", updatedSong);
        return updatedSong;
    }
}
