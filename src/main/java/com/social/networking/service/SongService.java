package com.social.networking.service;

import com.socail.networking.exception.ApplicationException;
import com.social.networking.dto.PublishSongDTO;

public interface SongService {

	boolean publishSong(PublishSongDTO publishSongDTO) throws ApplicationException;

	String getMostPopularSong() throws ApplicationException;

}
