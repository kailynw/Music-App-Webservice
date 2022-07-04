package com.restendpoints.musicapp.entity;

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
    @Getter
    private Long songId;
    @NotNull
    @Getter @Setter
    private String songName;
    @Getter
    @Setter
    private String imageUriLocation;
    @Getter @Setter
    private String songUriLocation;
    @Getter @Setter
    private Long numberOfLikes;
    @NotNull
    @Getter @Setter
    private String postedDate;
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
