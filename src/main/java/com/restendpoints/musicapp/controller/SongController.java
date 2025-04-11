package com.restendpoints.musicapp.controller;

import com.restendpoints.musicapp.dto.song.SongRequestDTO;
import com.restendpoints.musicapp.dto.song.SongResponseDTO;
import com.restendpoints.musicapp.dto.song.SongViewsRequestDTO;
import com.restendpoints.musicapp.entity.Song;
import com.restendpoints.musicapp.service.SongService;
import com.restendpoints.musicapp.util.DTOUtil;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;

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
    public ResponseEntity<SongResponseDTO> getSong(@PathVariable Long songId){
        Song song = songService.getSong(songId);
        SongResponseDTO songResponseDTO = DTOUtil.toSongResponseDTO(song);
        return ResponseEntity.ok(songResponseDTO);
    }

    @RequestMapping(value = "/users/{userId}/songs", method = GET)
    public ResponseEntity<List<SongResponseDTO>> getSongsByPostedUserId(@PathVariable Long userId){
        List<Song> songList = songService.getSongsByUserId(userId);
        List<SongResponseDTO> songResponseDTOList = DTOUtil.toSongResponseDTOList(songList);
        return ResponseEntity.ok(songResponseDTOList);
    }

    @RequestMapping(value = "/users/{userId}/songs/{songId}", method = GET)
    public ResponseEntity<SongResponseDTO> getSongByUserIdAndSongId(@PathVariable Long userId, @PathVariable Long songId){
        Song song = songService.getSongByUserIdAndSongId(userId, songId);
        SongResponseDTO songResponseDTO = DTOUtil.toSongResponseDTO(song);
        return ResponseEntity.ok(songResponseDTO);
    }

    @RequestMapping(value = "/songs/{songId}/presigned-url", method = GET)
    public ResponseEntity<SongResponseDTO> getSongPresignedUrl(@PathVariable Long songId){
        Song song = songService.getSongPresignedUrl(songId);
        SongResponseDTO songResponseDTO = DTOUtil.toSongResponseDTO(song);
        return ResponseEntity.ok(songResponseDTO);
    }

    /*POST Endpoints */
    @RequestMapping(value = "/users/{userId}/song", method = POST)
    public ResponseEntity<SongResponseDTO> createSong( @PathVariable Long userId, @RequestBody SongRequestDTO songRequestDTO){
        Song createdSong = songService.createSong(songRequestDTO, userId);
        SongResponseDTO songResponseDTO = DTOUtil.toSongResponseDTO(createdSong);
        return ResponseEntity.ok(songResponseDTO);
    }

    /*PATCH Endpoints */
    @RequestMapping(value = "/users/{userId}/song/{songId}", method = PATCH)
    public ResponseEntity<SongResponseDTO> updateSong(@PathVariable Long userId, @PathVariable Long songId,
                                                      @RequestBody SongRequestDTO songRequestDTO){
        Song updatedSong = songService.updateSong(songRequestDTO, userId, songId);
        SongResponseDTO songResponseDTO = DTOUtil.toSongResponseDTO(updatedSong);
        return ResponseEntity.ok(songResponseDTO);
    }

    @RequestMapping(value = "/users/{userId}/song/{songId}/views", method = PATCH)
    public ResponseEntity<SongResponseDTO> updateSongViews(@PathVariable Long userId, @PathVariable Long songId,
                                                           @RequestBody SongViewsRequestDTO songViewsRequestDTO){
       Song updatedSong = songService.updateSongViews(songViewsRequestDTO, userId, songId);
       SongResponseDTO songResponseDTO = DTOUtil.toSongResponseDTO(updatedSong);
       return ResponseEntity.ok(songResponseDTO);
    }

    /*PUT Endpoints */

}
