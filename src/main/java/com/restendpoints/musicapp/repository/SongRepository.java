package com.restendpoints.musicapp.repository;


import com.restendpoints.musicapp.entity.Song;
import com.restendpoints.musicapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findSongByPostedUser(User postedUser);
    Song findBySongByPostedUserAndSongId(User postedUser, Long songId);
}
