package com.restendpoints.musicapp.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import static com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.restendpoints.musicapp.dto.song.SongResponseDTO;
import com.restendpoints.musicapp.entity.Follower;
import com.restendpoints.musicapp.entity.Song;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

public class UserResponseDTO {

    @Getter @Setter
    private Long userId;

    @Getter @Setter
    private String userName;

    @JsonInclude(Include.NON_EMPTY)
    @Getter @Setter
    private Long numberOfFollowing;

    @JsonInclude(Include.NON_EMPTY)
    @Getter @Setter
    private Long numberOfFollowers;

    @JsonInclude(Include.NON_EMPTY)
    @Getter @Setter
    private String bio;

    @JsonInclude(Include.NON_EMPTY)
    @Getter @Setter
    private String instagramUrl;

    @JsonInclude(Include.NON_EMPTY)
    @Getter @Setter
    private String createdDate;

    @JsonProperty("songList")
    @JsonInclude(Include.NON_EMPTY)
    @Getter @Setter
    private List<SongResponseDTO> songResponseDTOList;

//    @Getter
//    private List<Follower> followerList;
//
//    @Getter
//    private List<Follower> followedList;

}
