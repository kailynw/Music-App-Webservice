package com.restendpoints.musicapp.service;

import com.restendpoints.musicapp.constants.Constants;
import com.restendpoints.musicapp.dto.song.SongRequestDTO;
import com.restendpoints.musicapp.dto.song.SongResponseDTO;
import com.restendpoints.musicapp.dto.song.SongViewsRequestDTO;
import com.restendpoints.musicapp.entity.Song;
import com.restendpoints.musicapp.entity.User;
import com.restendpoints.musicapp.repository.SongRepository;
import com.restendpoints.musicapp.repository.UserRepository;
import com.restendpoints.musicapp.util.DTOUtil;
import com.restendpoints.musicapp.util.DateUtil;
import com.restendpoints.musicapp.util.ValidationUtil;
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

    @Autowired
    ImageRetrievalService imageRetrievalService;

    @Autowired
    S3Service s3Service;

    public List<SongResponseDTO> getSongs(){
        List<Song> songList = songRepository.findAll();
        logger.info("Retrieved all songs: {}", songList);
        return DTOUtil.toSongResponseDTOList(songList);
    }

    public Song getSong(Long songId){
        Optional<Song> song = songRepository.findById(songId);
        boolean songExists = song.isPresent();

        if(songExists){
            logger.info("Retrieved song: {}",song);
            return song.get();
        }else {
            logger.warn("Song does not exist...");
            return null;
        }
    }

    public Song createSong(SongRequestDTO songRequestDTO, Long userId){
        Song song = DTOUtil.toSong(songRequestDTO);

        Optional<User> potentialUser = userRepository.findById(userId);
        boolean userExists = potentialUser.isPresent();

        if(userExists) {
            //Set postedByUser foreign key
            User user = potentialUser.get();
            song.setPostedUser(user);

            //Set song created date
            String today = DateUtil.getTodaysDate();
            song.setPostedDate(today);

            //Set song number of likes
            song.setNumberOfLikes(Constants.ZERO);

            //Set song views
            song.setNumberOfViews(Constants.ZERO);

            //Set random image
            song.setImageUriLocation(imageRetrievalService.retrieveRandomImageUrl());

            //Create song
            Song createdSong = songRepository.save(song);
            logger.info("Song created: {}", createdSong);
            return createdSong;
        }else{
            logger.error("User doesn't exist | Potential user id: {}", userId);
            return null;
        }
    }

    public Song updateSong(SongRequestDTO songRequestDTO, Long userId, Long songId){
        Song songUpdate = DTOUtil.toSong(songRequestDTO);
        songUpdate.setSongId(songId);

        Long potentialSongId = songUpdate.getSongId();
        Optional<Song> potentialSong = songRepository.findById(potentialSongId);
        boolean songExists = potentialSong.isPresent();

        if (songExists){
            Song existingSong = potentialSong.get();
            boolean userPostedSong = existingSong.getPostedUser().getUserId().equals(userId);

            if (userPostedSong) {
                Song updatedSong = this.handleSongUpdate(songUpdate, existingSong);
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

    public Song updateSongViews(SongViewsRequestDTO songViewsRequestDTO, Long userId, Long songId){
        Song songUpdate = DTOUtil.toSong(songViewsRequestDTO);
        Song existingSong = this.getSongByUserIdAndSongId(userId, songId);

        //Song exists
        if(existingSong!=null){
            Song updatedSong = this.handleSongUpdate(songUpdate, existingSong);
            return updatedSong;
        }
        else{
            return null;
        }
    }

    public List<Song> getSongsByUserId(Long userId) {
        Optional<User> potentialUser = userRepository.findById(userId);
        boolean userExists = potentialUser.isPresent();

        if (userExists) {
            User postedUser = potentialUser.get();
            List<Song> songListByUserId= songRepository.findSongsByPostedUser(postedUser);
            updateSongListWithPresignedUrls(songListByUserId);
            return songListByUserId;
        } else{
            logger.error("User doesn't exist. Could not get songs posted by user | User id: {}", userId);
            return null;
        }
    }

    public Song getSongByUserIdAndSongId(Long userId, Long songId) {
        boolean songExists = songRepository.existsById(songId);
        Optional<User> potentialUser = userRepository.findById(userId);
        boolean userExists = potentialUser.isPresent();

        if (songExists && userExists) {
            User postedUser = potentialUser.get();
            Song songByUserId = songRepository.findSongByPostedUserAndSongId(postedUser, songId);
            return songByUserId;
        } else{
            logger.error("User doesn't exist or Song doesn't exist" +
                    " | User id: {} | Song id: {}", userId, songId);
            return null;
        }
    }

    public Song getSongPresignedUrl(Long songId){
        Optional<Song> potentialSong = songRepository.findById(songId);

        if (potentialSong.isPresent()){
            Song song = potentialSong.get();
            String presignedSongUrl = s3Service.getPresignedUrl(song.getSongUriLocation());
            song.setSongUriLocation(presignedSongUrl);
            logger.info(presignedSongUrl);
            return song;
        }else{
            logger.error("Song doesn't exist: {}", songId);
            return null;
        }
    }

    private Song handleSongUpdate(Song songUpdate, Song existingSong){
        String songName = songUpdate.getSongName();
        String description = songUpdate.getDescription();
        String imageUriLocation = songUpdate.getImageUriLocation();
        String songUriLocation = songUpdate.getSongUriLocation();
        Long numberOfViews = songUpdate.getNumberOfViews();

        if (ValidationUtil.isUpdatable(existingSong.getSongName(), songName)){
            logger.info("Song id: {} | Updating song name: {} -> {}", existingSong.getSongId(), existingSong.getSongName(), songName);
            existingSong.setSongName(songName);
        }

        if (ValidationUtil.isUpdatable(existingSong.getDescription(), description)){
            logger.info("Song id: {} | Updating song description: {} -> {}", existingSong.getSongId(), existingSong.getDescription(), description);
            existingSong.setDescription(description);
        }

        if (ValidationUtil.isUpdatable(existingSong.getImageUriLocation(), imageUriLocation)){
            logger.info("Song id: {} | Updating song image URI location: {} -> {}", existingSong.getSongId(), existingSong.getImageUriLocation(), imageUriLocation);
            existingSong.setImageUriLocation(imageUriLocation);
        }

        if (ValidationUtil.isUpdatable(existingSong.getSongUriLocation(), songUriLocation)){
            logger.info("Song id: {} | Updating song URI location: {} -> {}", existingSong.getSongId(), existingSong.getSongUriLocation(), songUriLocation);
            existingSong.setSongUriLocation(songUriLocation);
        }

        if(ValidationUtil.isUpdatableAscendingOnly(existingSong.getNumberOfViews(), numberOfViews)){
            logger.info("Song id: {} | Updating song views: {} -> {}", existingSong.getSongId(), existingSong.getNumberOfViews(), numberOfViews);
            existingSong.setNumberOfViews(numberOfViews);
        }

        Song updatedSong = songRepository.save(existingSong);
        logger.info("Updated song: {}", updatedSong);
        return updatedSong;
    }

    private void updateSongListWithPresignedUrls(List<Song> songList){
        for (Song song: songList) {
            if (StringUtils.isNotEmpty(song.getImageUriLocation())) {
                String presignedUrl = s3Service.getPresignedUrl(song.getSongUriLocation());
                song.setSongPresignedUrl(presignedUrl);
            }
        }
    }

}
