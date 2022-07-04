package com.restendpoints.musicapp.controller;

import com.restendpoints.musicapp.entity.Song;
import com.restendpoints.musicapp.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static  org.springframework.web.bind.annotation.RequestMethod.GET;
import static  org.springframework.web.bind.annotation.RequestMethod.POST;
import static  org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

@RestController
public class SongController {
    @Autowired
    SongService songService;

    @RequestMapping(value = "/songs", method = GET)
    public ResponseEntity<List<Song>> getSongs(){
        List<Song> songList = songService.getSongs();
        return ResponseEntity.ok(songList);
    }

    @RequestMapping(value = "/songs/{songId}", method = GET)
    public ResponseEntity<Song> getSong(@PathVariable("songId") Long songId){
        Song song = songService.getSong(songId);
        return ResponseEntity.ok(song);
    }

    @RequestMapping(value = "/user/{userId}/song", method = POST)
    public Song createSong(@RequestBody Song song, @PathVariable("userId") Long userId){
        Song createdSong = songService.createSong(song, userId);
        return createdSong;
    }

    @RequestMapping(value = "/user/{userId}/song", method = PUT)
    public Song updateSong(@RequestBody Song song, @PathVariable("userId") Long userId){
        Song updateSong = songService.updateSong(song, userId);
        return updateSong;
    }

    @RequestMapping(value = "/user/{userId}/songs", method = GET)
    public List<Song> getSongByPostedUserId(@RequestBody Song song, @PathVariable("userId") Long userId){
        List<Song> songList = songService.getSongsByPostedUserId(userId);
        return songList;
    }

    @RequestMapping(value = "/user/{userId}/song/{songId}", method = GET)
    public Song getSongByUserIdAndSongId(@PathVariable("userId") Long userId, @PathVariable("songId") Long songId){
        Song songByUserId = songService.getSongsByUserIdAndSongId(userId, songId);
        return songByUserId;
    }
}
