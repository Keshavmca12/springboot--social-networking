package com.social.networking.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socail.networking.exception.ApplicationException;
import com.social.networking.constant.BadRequestException;
import com.social.networking.dto.FollowArtistDTO;
import com.social.networking.entity.Artist;
import com.social.networking.entity.Song;
import com.social.networking.entity.User;
import com.social.networking.entity.UserPlaylist;
import com.social.networking.repository.ArtistRepository;
import com.social.networking.repository.UserPlaylistRepository;
import com.social.networking.repository.UserRepository;
import com.social.networking.service.ArtistService;

@Service
@Transactional
public class ArtistServiceImpl implements ArtistService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ArtistServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ArtistRepository artistRepository;


	@Autowired
	private UserPlaylistRepository userPlaylistRepository;

	@Override
	public User followArtist(FollowArtistDTO followDTO) throws ApplicationException {
		if (null == followDTO.getUser()) {
			throw new BadRequestException("Please provide user details");
		}

		if (null == followDTO.getArtist() || followDTO.getArtist().size() == 0) {
			throw new BadRequestException("Please provide artist details");
		}
		List<Artist> artists = new ArrayList<>();
		Optional<User> user = userRepository.findByUserName(followDTO.getUser());
		/**
		 * Create user if not present
		 */
		if (!user.isPresent()) {
			user = Optional.of(createUser(followDTO.getUser()));
		}
		User userEntity = user.get();

		for (String artistName : followDTO.getArtist()) {
			Optional<Artist> artist = artistRepository.findByArtistName(artistName);
			if (!artist.isPresent()) {
				artist = Optional.of(saveArtist(artistName));
			}
			/**
			 * Check if artist is already followed by user
			 */
			if (artist.get().getUsers().contains(userEntity)) {
				throw new ApplicationException("User is already following this artist");
			}
			artists.add(artist.get());
			/**
			 * Add song of artist to user playlist
			 */
			addArtistSongInPlaylist(userEntity, artist.get());
		}

		/**
		 * Add artist to user
		 */
		userEntity.getArtists().addAll(artists);

		userEntity = userRepository.save(userEntity);
		LOGGER.info("User artist and playlist updated: " + userEntity.getUserName());
		System.out.println(userEntity);

		return userEntity;
	}

	@Override
	public User unfollowArtist(FollowArtistDTO followDTO) throws ApplicationException {
		if (null == followDTO.getUser()) {
			throw new BadRequestException("Please provide user details");
		}

		if (null == followDTO.getArtist() || followDTO.getArtist().size() == 0) {
			throw new BadRequestException("Please provide artist details");
		}
		Optional<User> user = userRepository.findByUserName(followDTO.getUser());

		if (!user.isPresent()) {
			throw new ApplicationException("User does not exist");
		}
		User userEntity = user.get();

		for (String artistName : followDTO.getArtist()) {

			Optional<Artist> artist = artistRepository.findByArtistName(artistName);
			if (!artist.isPresent()) {
				throw new ApplicationException("Artist does not exist");
			}

			/**
			 * Check if artist is already followed by user
			 */

			if (!artist.get().getUsers().contains(userEntity)) {
				throw new ApplicationException("User is not following this artist");
			}

			userEntity.getArtists().remove(artist.get());
			userPlaylistRepository.removeArtistSongFromPlayList(userEntity.getUserId(), artist.get().getArtistName());
		}

		userEntity = userRepository.save(userEntity);

		System.out.println(userEntity);

		return userEntity;
	}

	@Override
	public String getMostPopularArtist() throws ApplicationException {
		List<Object[]> list = artistRepository.getMostPopularArtist();
		if (list.size() == 0) {
			throw new ApplicationException("Artist not found");
		}
		return (String) list.get(0)[1];
	}

	@Override
	public Integer getArtistFollowerCount(String artistName) {
		if (null == artistName) {
			return -1;
		}

		Optional<Artist> artist = artistRepository.findByArtistName(artistName);
		if (!artist.isPresent()) {
			return -1;
		}
		return artist.get().getUsers().size();
	}

	private void addArtistSongInPlaylist(User user, Artist artist) {
		for (Song song : artist.getSongs()) {
			updateUserPlaylist(user.getUserId(), artist.getArtistName(), song.getSongName());
		}
	}

	private User createUser(String userName) {
		User userData = new User();
		userData.setUserName(userName);
		return userRepository.save(userData);
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
