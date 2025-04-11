package com.restendpoints.musicapp.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Auth0UserDataDTO {
    private String email;
    private String profilePictureUrl;

    @Override
    public String toString() {
        return "Auth0UserDataDTO{" +
                "email='" + email + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                '}';
    }
}
