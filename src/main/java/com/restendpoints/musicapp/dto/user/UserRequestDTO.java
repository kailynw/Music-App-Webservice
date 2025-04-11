package com.restendpoints.musicapp.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

    private String userName;

    private String bio;

    private String instagramUrl;

    private String profilePictureUrl;

}
