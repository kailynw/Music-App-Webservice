package com.restendpoints.musicapp.service;

import com.restendpoints.musicapp.constants.Constants;
import com.restendpoints.musicapp.dto.user.UserRequestDTO;
import com.restendpoints.musicapp.dto.user.UserResponseDTO;
import com.restendpoints.musicapp.entity.User;
import com.restendpoints.musicapp.repository.UserRepository;
import com.restendpoints.musicapp.util.DTOUtil;
import com.restendpoints.musicapp.util.DateUtil;
import com.restendpoints.musicapp.util.ValidationUtil;
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

    public List<UserResponseDTO> getUsers(){
        List<User> userList = userRepository.findAll();
        return DTOUtil.toUserResponseDTOList(userList);
    }

    public UserResponseDTO getUser(Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            logger.warn("User does not exist..");
            return null;
        }else{
            logger.info("User retrieved: {}", user);
            return DTOUtil.toUserResponseDTO(user.get());
        }
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){
        User user = DTOUtil.toUser(userRequestDTO);
        String today = DateUtil.getTodaysDate();

        user.setCreatedDate(today);
        user.setNumberOfFollowers(Constants.ZERO);
        user.setNumberOfFollowing(Constants.ZERO);

        User createdUser = this.userRepository.save(user);
        logger.info("User created: {}", createdUser);
        return DTOUtil.toUserResponseDTO(createdUser);
    }

    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO, Long userId) {
        User user = DTOUtil.toUser(userRequestDTO);
        user.setUserId(userId);

        Long potentialUserId = user.getUserId();
        Optional<User> potentialExistingUser = userRepository.findById(potentialUserId);
        boolean userExists = potentialExistingUser.isPresent();

        if (userExists) {
            User existingUser = potentialExistingUser.get();
            User updatedUser = this.handleUserUpdate(user, existingUser);
            return DTOUtil.toUserResponseDTO(updatedUser);
        } else {
            logger.error("User doesn't exist. Could not update");
            return null;
        }
    }

    private User handleUserUpdate(User userUpdate, User existingUser){
        String username = userUpdate.getUserName();
        String bio = userUpdate.getBio();
        String instagramUrl = userUpdate.getInstagramUrl();

        if (ValidationUtil.isUpdatable(existingUser.getUserName(), username)){
            logger.info("User id: {} | Updating user name: {} -> {}", existingUser.getUserId(), existingUser.getUserName(), username);
            existingUser.setUserName(username);
        }

        if (ValidationUtil.isUpdatable(existingUser.getBio(), bio)){
            logger.info("User id: {} | Updating user bio: {} -> {}", existingUser.getUserId(), existingUser.getBio(), bio);
            existingUser.setBio(bio);
        }

        if (ValidationUtil.isUpdatable(existingUser.getInstagramUrl(), instagramUrl)){
            logger.info("User id: {} | Updating user instagram Url: {} -> {}", existingUser.getUserId(), existingUser.getInstagramUrl(), instagramUrl);
            existingUser.setInstagramUrl(instagramUrl);
        }

        User updatedUser = userRepository.save(existingUser);
        logger.info("Updated user: {}", updatedUser);
        return updatedUser;
    }
}
