package com.restendpoints.musicapp.dto.song;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongRequestDTO {

    private String songName;

    private String description;

    private String imageUriLocation;

    private String songUriLocation;

}
