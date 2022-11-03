package com.restendpoints.musicapp.controller;

import com.restendpoints.musicapp.dto.song.SongRequestDTO;
import com.restendpoints.musicapp.dto.song.SongResponseDTO;
import com.restendpoints.musicapp.entity.Song;
import com.restendpoints.musicapp.service.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SongController {
    public static final Logger logger = LoggerFactory.getLogger(SongController.class);

    @Autowired
    SongService songService;

    /* GET Endpoints */
    @RequestMapping(value = "/songs", method = GET)
    public ResponseEntity<List<SongResponseDTO>> getSongs(){
        List<SongResponseDTO> songList = songService.getSongs();
        return ResponseEntity.ok(songList);
    }

    @RequestMapping(value = "/songs/{songId}", method = GET)
    public ResponseEntity<SongResponseDTO> getSong(@PathVariable("songId") Long songId){
        SongResponseDTO song = songService.getSong(songId);
        return ResponseEntity.ok(song);
    }

    @RequestMapping(value = "/users/{userId}/songs", method = GET)
    public ResponseEntity<List<SongResponseDTO>> getSongsByPostedUserId(@PathVariable("userId") Long userId){
        List<SongResponseDTO> songList = songService.getSongsByUserId(userId);
        return ResponseEntity.ok(songList);
    }

    @RequestMapping(value = "/users/{userId}/songs/{songId}", method = GET)
    public ResponseEntity<SongResponseDTO> getSongByUserIdAndSongId(@PathVariable Long userId, @PathVariable Long songId){
        SongResponseDTO song = songService.getSongByUserIdAndSongId(userId, songId);
        return ResponseEntity.ok(song);
    }

    /*POST Endpoints */
    @RequestMapping(value = "/users/{userId}/song", method = POST)
    public ResponseEntity<SongResponseDTO> createSong( @PathVariable Long userId, @RequestBody SongRequestDTO songRequestDTO){
        SongResponseDTO createdSong = songService.createSong(songRequestDTO, userId);
        return ResponseEntity.ok(createdSong);
    }
    /*PUT Endpoints */
    @RequestMapping(value = "/users/{userId}/song/{songId}", method = PATCH)
    public ResponseEntity<SongResponseDTO> updateSong(@PathVariable Long userId, @PathVariable Long songId, @RequestBody SongRequestDTO songRequestDTO){
        SongResponseDTO updateSong = songService.updateSong(songRequestDTO, userId, songId);
        return ResponseEntity.ok(updateSong);
    }
}
