package com.restendpoints.musicapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.FetchType.LAZY;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "song")
public class Song {
    @Id
    @GeneratedValue(strategy = IDENTITY)
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
    @JsonBackReference //Resolve Bidirectional Circular Dependency "songList"
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId")
    @Getter @Setter
    private User postedUser;

    @Override
    public String toString() {
        return "Song{" +
                "songId=" + songId +
                ", songName='" + songName + '\'' +
                ", imageUriLocation='" + imageUriLocation + '\'' +
                ", songUriLocation='" + songUriLocation + '\'' +
                ", numberOfLikes=" + numberOfLikes +
                ", postedDate=" + postedDate +
                ", postedByUser=" + postedUser +
                '}';
    }
}
