package com.restendpoints.musicapp.dto.song;

import com.fasterxml.jackson.annotation.JsonInclude;
import static com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.restendpoints.musicapp.dto.user.UserResponseDTO;
import com.restendpoints.musicapp.entity.User;
import lombok.Getter;
import lombok.Setter;

public class SongResponseDTO {
    @Getter @Setter
    private Long songId;

    @Getter @Setter
    private String songName;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private String imageUriLocation;

    @Getter @Setter
    private String songUriLocation;

    @Getter @Setter
    private Long numberOfLikes;

    @Getter @Setter
    private String postedDate;

    @JsonProperty("postedUser")
    @JsonInclude(Include.NON_EMPTY)
    @Getter @Setter
    private UserResponseDTO postedUserResponseDTO;

    @Override
    public String toString() {
        return "SongResponseDTO{" +
                "songId=" + songId +
                ", songName='" + songName + '\'' +
                ", imageUriLocation='" + imageUriLocation + '\'' +
                ", songUriLocation='" + songUriLocation + '\'' +
                ", numberOfLikes=" + numberOfLikes +
                ", postedDate=" + postedDate +
                ", postedByUser=" + postedUserResponseDTO +
                '}';
    }

}
