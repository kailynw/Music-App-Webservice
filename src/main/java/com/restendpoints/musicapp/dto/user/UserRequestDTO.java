package com.restendpoints.musicapp.dto.user;

import lombok.Getter;
import lombok.Setter;

public class UserRequestDTO {

    @Getter @Setter
    private String userName;

    @Getter @Setter
    private String bio;

    @Getter @Setter
    private String instagramUrl;

}
