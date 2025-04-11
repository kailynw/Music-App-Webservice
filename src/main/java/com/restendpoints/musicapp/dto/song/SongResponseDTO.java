package com.restendpoints.musicapp.dto.song;

import com.fasterxml.jackson.annotation.JsonInclude;
import static com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.restendpoints.musicapp.dto.user.UserResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongResponseDTO {


    private Long songId;

    private String songName;
    private String description;
    private String imageUriLocation;
    private String songUriLocation;

    private Long numberOfLikes;

    private Long numberOfViews;

    private String postedDate;

    private String songPresignedUrl;


    @JsonProperty("postedUser")
    @JsonInclude(Include.NON_EMPTY)
    private UserResponseDTO postedUserResponseDTO;

    @Override
    public String toString() {
        return "SongResponseDTO{" +
                "songId=" + songId +
                ", songName='" + songName + '\'' +
                ", description='" + description + '\'' +
                ", imageUriLocation='" + imageUriLocation + '\'' +
                ", songUriLocation='" + songUriLocation + '\'' +
                ", numberOfLikes=" + numberOfLikes +
                ", numberOfViews=" + numberOfViews +
                ", postedDate='" + postedDate + '\'' +
                ", songPresignedUrl='" + songPresignedUrl + '\'' +
                ", postedUserResponseDTO=" + postedUserResponseDTO +
                '}';
    }
}
