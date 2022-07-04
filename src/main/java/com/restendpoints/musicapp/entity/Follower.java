package com.restendpoints.musicapp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.FetchType.LAZY;
@Entity
@Table (name = "follower")
public class Follower {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Getter
    private Long followerConnectionId;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "followed_user_id", referencedColumnName = "userId")
    @NotNull
    @Getter @Setter
    private User followedUser; //User who is being followed
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "follower_user_id", referencedColumnName = "userId")
    @NotNull
    @Getter @Setter
    private User followerUser; //User who is following the "followedUser"
    @NotNull
    @Getter @Setter
    private String connectionDate;
}
