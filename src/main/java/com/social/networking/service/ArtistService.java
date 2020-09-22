package com.social.networking.service;

import com.socail.networking.exception.ApplicationException;
import com.social.networking.dto.FollowArtistDTO;
import com.social.networking.entity.User;

public interface ArtistService {

	User followArtist(FollowArtistDTO followDTO) throws ApplicationException;

	User unfollowArtist(FollowArtistDTO followDTO) throws ApplicationException;

	String getMostPopularArtist() throws ApplicationException;

	Integer getArtistFollowerCount(String artist);

}
