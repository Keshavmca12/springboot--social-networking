package com.social.networking.service;

import java.util.Set;

import com.socail.networking.exception.ApplicationException;
import com.social.networking.dto.FollowArtistDTO;
import com.social.networking.dto.PublishSongDTO;
import com.social.networking.entity.User;

public interface UserService {

	User followArtist(FollowArtistDTO followDTO) throws ApplicationException;

	boolean publishSong(PublishSongDTO publishSongDTO) throws ApplicationException;

	User unfollowArtist(FollowArtistDTO followDTO) throws ApplicationException;

	Set<String> getPlaylist(String user) throws ApplicationException;

	String getMostPopularSong();

	String getMostPopularArtist();

	Integer getArtistFollowerCount(String artist);

}
