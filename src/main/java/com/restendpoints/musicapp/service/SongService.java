package com.restendpoints.musicapp.service;

import com.restendpoints.musicapp.entity.Song;
import com.restendpoints.musicapp.entity.User;
import com.restendpoints.musicapp.repository.SongRepository;
import com.restendpoints.musicapp.repository.UserRepository;
import com.restendpoints.musicapp.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    private static final Logger logger = LoggerFactory.getLogger(SongService.class);

    @Autowired
    SongRepository songRepository;

    @Autowired
    UserRepository userRepository;


    public List<Song> getSongs(){
        List<Song> songList = songRepository.findAll();
        logger.info("Retrieved all songs: {}", songList);
        return songList;
    }

    public Song getSong(Long songId){
        Optional<Song> song = songRepository.findById(songId);
        boolean songExists = song.isPresent();

        if(songExists){
            logger.info("Retrieved song: {}",song);
            return song.get();
        }else{
            logger.warn("Song does not exist...");
            return null;
        }
    }

    public Song createSong(Song song, Long userId){
        Optional<User> potentialUser = userRepository.findById(userId);
        boolean userExists = potentialUser.isPresent();

        if(userExists) {
            //Set postedByUser foreign key
            User user = potentialUser.get();
            song.setPostedUser(user);

            String today = DateUtil.getTodayDate();
            song.setPostedDate(today);

            //Create song
            Song createdSong = songRepository.save(song);
            logger.info("Song created: {}", song);
            return createdSong;
        }else{
            logger.error("User doesn't exist | Potential user id: {}", userId);
            return null;
        }
    }

    public Song updateSong(Song song, Long userId){
        Long songId = song.getSongId();
        Optional<Song> potentialSong = songRepository.findById(songId);
        boolean songExists = potentialSong.isPresent();

        if (songExists){
            Song existingSong = potentialSong.get();
            boolean userPostedSong = existingSong.getPostedUser().getUserId().equals(userId);

            if (userPostedSong) {
                Song updatedSong = this.handleSongUpdate(song, existingSong);
                return updatedSong;
            } else {
                logger.error("Could not update song. User is not original poster");
                return null;
            }
        }else{
            logger.error("Song doesn't exist. Could not update song");
            return null;
        }
    }

    public List<Song> getSongsByPostedUserId(Long userId) {
        Optional<User> potentialUser = userRepository.findById(userId);
        boolean userExists = potentialUser.isPresent();

        if (userExists) {
            User postedUser = potentialUser.get();
            List<Song> songListByUserId = songRepository.findSongByPostedUser(postedUser);
            return songListByUserId;
        } else{
            logger.error("User doesn't exist. Could not get songs posted by user | User id: {}", userId);
            return null;
        }
    }

    public Song getSongsByUserIdAndSongId(Long userId, Long songId) {
        boolean songExists = songRepository.existsById(songId);
        Optional<User> potentialUser = userRepository.findById(userId);
        boolean userExists = potentialUser.isPresent();

        if (songExists && userExists) {
            User postedUser = potentialUser.get();
            Song songByUserId = songRepository.findBySongByPostedUserAndSongId(postedUser, songId);
            return songByUserId;
        } else{
            logger.error("User doesn't exist or song doesn't exist" +
                    " | User id: {} | Song id: {}", userId, songId);
            return null;
        }
    }

    private Song handleSongUpdate(Song songUpdate, Song existingSong){
        String songName = songUpdate.getSongName();

        if (!StringUtils.isEmpty(songName)){
            existingSong.setSongName(songName);
            logger.info("Song id: {} | Updating song name: {} -> {}", existingSong.getSongId(), existingSong.getSongName(), songName);
        }

        Song updatedSong = songRepository.save(existingSong);
        logger.info("Updated song: {}", updatedSong);
        return updatedSong;
    }
}
