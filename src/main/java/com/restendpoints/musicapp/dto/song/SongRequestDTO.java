package com.restendpoints.musicapp.dto.song;

import lombok.Getter;
import lombok.Setter;

public class SongRequestDTO {

    @Getter @Setter
    private String songName;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private String imageUriLocation;

    @Getter @Setter
    private String songUriLocation;

}
