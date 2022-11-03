package com.restendpoints.musicapp.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.CascadeType.ALL;

//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "userId")
@Entity
@Table(name = "platform_user") // The table name "user" is prohibited in PostgresSQL
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Getter @Setter
    private Long userId;
    @Getter @Setter
    private String userName;
    @Getter @Setter
    private Long numberOfFollowing;
    @Getter @Setter
    private Long numberOfFollowers;
    @Getter @Setter
    private String bio;
    @Getter @Setter
    private String instagramUrl;
    @Getter @Setter
    private String createdDate;
    @JsonManagedReference //Resolve Bidirectional Circular Dependency with "postedUser"
    @OneToMany(mappedBy = "postedUser", fetch = LAZY, cascade = ALL)
    @Getter @Setter
    private List<Song> songList;
    @OneToMany(mappedBy = "followedUser", fetch = LAZY, cascade = ALL)
    @Getter
    private List<Follower> followerList;

    @OneToMany(mappedBy = "followerUser", fetch = LAZY, cascade = ALL)
    @Getter
    private List<Follower> followedList;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", numberOfFollowing=" + numberOfFollowing +
                ", numberOfFollowers=" + numberOfFollowers +
                ", bio='" + bio + '\'' +
                ", instagramUrl='" + instagramUrl + '\'' +
                ", createdDate=" + createdDate +
//                ", songList=" + songList +
                ", followerList=" + followerList +
                '}';
    }
}
