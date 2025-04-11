package com.restendpoints.musicapp.util;

import com.restendpoints.musicapp.dto.song.SongRequestDTO;
import com.restendpoints.musicapp.dto.song.SongResponseDTO;
import com.restendpoints.musicapp.dto.song.SongViewsRequestDTO;
import com.restendpoints.musicapp.dto.user.UserRequestDTO;
import com.restendpoints.musicapp.dto.user.UserResponseDTO;
import com.restendpoints.musicapp.entity.Song;
import com.restendpoints.musicapp.entity.User;

import java.util.ArrayList;
import java.util.List;

public class DTOUtil {

   /*-----Song Request and Song Response DTO's -----*/
    public static SongResponseDTO toSongResponseDTO(Song song){
       SongResponseDTO songResponseDTO = new SongResponseDTO();
       UserResponseDTO postedUserResponseDTO = toSummarizedUserResponseDTO(song.getPostedUser());

       songResponseDTO.setSongId(song.getSongId());
       songResponseDTO.setSongName(song.getSongName());
       songResponseDTO.setDescription(song.getDescription());
       songResponseDTO.setSongUriLocation(song.getSongUriLocation());
       songResponseDTO.setImageUriLocation(song.getImageUriLocation());
       songResponseDTO.setNumberOfLikes(song.getNumberOfLikes());
       songResponseDTO.setNumberOfViews(song.getNumberOfViews());
       songResponseDTO.setPostedDate(song.getPostedDate());
       songResponseDTO.setSongPresignedUrl(song.getSongPresignedUrl());
       songResponseDTO.setPostedUserResponseDTO(postedUserResponseDTO);

       return songResponseDTO;
    }

   public static SongResponseDTO toSummarizedSongResponseDTO(Song song){
      SongResponseDTO songResponseDTO = new SongResponseDTO();

      songResponseDTO.setSongId(song.getSongId());
      songResponseDTO.setSongName(song.getSongName());
      songResponseDTO.setDescription(song.getDescription());
      songResponseDTO.setSongUriLocation(song.getSongUriLocation());
      songResponseDTO.setImageUriLocation(song.getImageUriLocation());
      songResponseDTO.setNumberOfLikes(song.getNumberOfLikes());
      songResponseDTO.setPostedDate(song.getPostedDate());

      return songResponseDTO;
   }

   public static List<SongResponseDTO> toSongResponseDTOList(List<Song> songList){
       List<SongResponseDTO> songResponseDTOList = new ArrayList<>();

       for(Song song: songList){
          songResponseDTOList.add(toSongResponseDTO(song));
       }

       return songResponseDTOList;
    }

    public static Song toSong(SongRequestDTO songRequestDTO){
       Song song = new Song();

       song.setSongName(songRequestDTO.getSongName());
       song.setDescription(songRequestDTO.getDescription());
       song.setImageUriLocation(songRequestDTO.getImageUriLocation());
       song.setSongUriLocation(songRequestDTO.getSongUriLocation());

       return song;
    }

   public static Song toSong(SongViewsRequestDTO songViewsRequestDTO){
      Song song = new Song();
      song.setNumberOfViews(songViewsRequestDTO.getNumberOfViews());
      return song;
   }


   /*----- User Request and User Response DTO's -----*/
   public static UserResponseDTO toUserResponseDTO(User user){
      UserResponseDTO userResponseDTO = new UserResponseDTO();
      List<SongResponseDTO> songResponseDTOList = new ArrayList<>();

      userResponseDTO.setUserId(user.getUserId());
      userResponseDTO.setUserName(user.getUserName());
      userResponseDTO.setNumberOfFollowing(user.getNumberOfFollowing());
      userResponseDTO.setNumberOfFollowers(user.getNumberOfFollowers());
      userResponseDTO.setBio(user.getBio());
      userResponseDTO.setInstagramUrl(user.getInstagramUrl());
      userResponseDTO.setProfilePictureUrl(user.getProfilePictureUrl());
      userResponseDTO.setCreatedDate(user.getCreatedDate());
      userResponseDTO.setEmail(user.getEmail());
      userResponseDTO.setNumberOfLogins(user.getNumberOfLogins());
      //Set songResponseDTOList in UserResponseDTO
      List<Song> songList = user.getSongList();
      if(songList!=null) {
         for (Song song : songList) {
            SongResponseDTO songResponseDTO = toSummarizedSongResponseDTO(song);
            songResponseDTOList.add(songResponseDTO);
         }
      }

      userResponseDTO.setSongResponseDTOList(songResponseDTOList);

      return userResponseDTO;
   }

   public static UserResponseDTO toSummarizedUserResponseDTO(User user){
       UserResponseDTO userResponseDTO = new UserResponseDTO();

       userResponseDTO.setUserId(user.getUserId());
       userResponseDTO.setUserName(user.getUserName());

       return userResponseDTO;
   }

   public static List<UserResponseDTO> toUserResponseDTOList(List<User> userList){
      List<UserResponseDTO> userResponseDTOList = new ArrayList<>();

      for(User user: userList){
         userResponseDTOList.add(toUserResponseDTO(user));
      }

      return userResponseDTOList;
   }

   public static User toUser(UserRequestDTO userRequestDTO){
      User user = new User();

      user.setUserName(userRequestDTO.getUserName());
      user.setBio(userRequestDTO.getBio());
      user.setInstagramUrl(userRequestDTO.getInstagramUrl());
      user.setProfilePictureUrl(userRequestDTO.getProfilePictureUrl());

      return user;
   }

}
