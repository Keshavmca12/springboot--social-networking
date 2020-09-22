package com.social.networking.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socail.networking.exception.ApplicationException;
import com.social.networking.constant.BadRequestException;
import com.social.networking.dto.PublishSongDTO;
import com.social.networking.entity.Artist;
import com.social.networking.entity.Song;
import com.social.networking.entity.User;
import com.social.networking.entity.UserPlaylist;
import com.social.networking.repository.ArtistRepository;
import com.social.networking.repository.SongRepository;
import com.social.networking.repository.UserPlaylistRepository;
import com.social.networking.service.SongService;

@Service
@Transactional
public class SongServiceImpl implements SongService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SongServiceImpl.class);

	@Autowired
	private ArtistRepository artistRepository;

	@Autowired
	private SongRepository songRepository;

	@Autowired
	private UserPlaylistRepository userPlaylistRepository;

	@Override
	public boolean publishSong(PublishSongDTO publishSongDTO) throws ApplicationException {
		if (null == publishSongDTO.getSong()) {
			throw new BadRequestException("Please provide song details");
		}

		if (null == publishSongDTO.getArtists() || publishSongDTO.getArtists().size() == 0) {
			throw new BadRequestException("Please provide artist details");
		}
		LOGGER.info("publishSong  : " + publishSongDTO.getSong() + " to artists  : " + publishSongDTO.getArtists());
		for (String artistName : publishSongDTO.getArtists()) {
			Optional<Artist> artist = artistRepository.findByArtistName(artistName);
			if (!artist.isPresent()) {
				artist = Optional.of(saveArtist(artistName));
			}

			Optional<Song> song = songRepository.findBySongName(publishSongDTO.getSong());
			if (!song.isPresent()) {
				song = Optional.of(saveSong(publishSongDTO.getSong()));
			}

			Artist artistEntity = artist.get();
			if (artistEntity.getSongs().contains(song.get())) {
				throw new ApplicationException("This song is already published by this artist");
			}

			/**
			 * Publish song to artist
			 */
			artistEntity.getSongs().add(song.get());

			/**
			 * Add song to user playlist
			 */
			for (User user : artistEntity.getUsers()) {
				updateUserPlaylist(user.getUserId(), artistName, song.get().getSongName());
			}
			artistRepository.save(artistEntity);

		}
		return true;
	}

	@Override
	public String getMostPopularSong() throws ApplicationException {
		List<Object[]> list = userPlaylistRepository.getMostPopularSong();
		
		if (list.size() == 0) {
			throw new ApplicationException("Song not found");
		}
		LOGGER.info("getMostPopularSong  : " + list.get(0));
		return (String) list.get(0)[1];
	}

	private Song saveSong(String songName) {
		Song song = new Song(songName);
		return songRepository.save(song);
	}

	private Artist saveArtist(String artistName) {
		Artist artistData = new Artist();
		artistData.setArtistName(artistName);
		return artistRepository.save(artistData);
	}

	private UserPlaylist updateUserPlaylist(int userId, String artistName, String songName) {
		UserPlaylist userPlaylist = new UserPlaylist();
		userPlaylist.setArtistName(artistName);
		userPlaylist.setUserId(userId);
		userPlaylist.setSongName(songName);
		return userPlaylistRepository.save(userPlaylist);
	}

}
