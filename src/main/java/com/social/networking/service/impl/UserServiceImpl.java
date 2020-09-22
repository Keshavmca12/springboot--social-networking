package com.social.networking.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socail.networking.exception.ApplicationException;
import com.social.networking.constant.BadRequestException;
import com.social.networking.dto.FollowArtistDTO;
import com.social.networking.dto.PublishSongDTO;
import com.social.networking.entity.Artist;
import com.social.networking.entity.Song;
import com.social.networking.entity.User;
import com.social.networking.entity.UserPlaylist;
import com.social.networking.repository.ArtistRepository;
import com.social.networking.repository.SongRepository;
import com.social.networking.repository.UserPlaylistRepository;
import com.social.networking.repository.UserRepository;
import com.social.networking.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ArtistRepository artistRepository;

	@Autowired
	private SongRepository songRepository;

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
	public boolean publishSong(PublishSongDTO publishSongDTO) throws ApplicationException {
		if (null == publishSongDTO.getSong()) {
			throw new BadRequestException("Please provide song details");
		}

		if (null == publishSongDTO.getArtists() || publishSongDTO.getArtists().size() == 0) {
			throw new BadRequestException("Please provide artist details");
		}

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
			if(artistEntity.getSongs().contains(song.get())) {
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
			
			if(!artist.get().getUsers().contains(userEntity)) {
				throw new ApplicationException("User is not following this artist");
			} 

			userEntity.getArtists().remove(artist.get());
			userPlaylistRepository.removeArtistSongFromPlayList(userEntity.getUserId(),
					artist.get().getArtistName());
		}

		userEntity = userRepository.save(userEntity);

		System.out.println(userEntity);

		return userEntity;
	}

	@Override
	public Set<String> getPlaylist(String userName) throws ApplicationException {
		Optional<User> user = userRepository.findByUserName(userName);

		if (!user.isPresent()) {
			throw new ApplicationException("User does not exist");
		}

		/***
		 * If user have followed multiple artist with that contains same song then only one will be added to user playlist
		 */
		Set<String> songs = new HashSet<>();
		List<UserPlaylist> userPlaylist = userPlaylistRepository
				.findByUserId(user.get().getUserId());
		for (UserPlaylist song : userPlaylist) {
			songs.add(song.getSongName());
		}
		return songs;
	}
	
	@Override
	public String getMostPopularSong() {
		List<Object[]> list = userPlaylistRepository.getMostPopularSong(); 
		if(list.size() == 0) {
			return null;
		}
		return (String) list.get(0)[1];
	}

	@Override
	public String getMostPopularArtist() {
		List<Object[]> list = artistRepository.getMostPopularArtist(); 
		if(list.size() == 0) {
			return null;
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

	private Song saveSong(String songName) {
		Song song = new Song(songName);
		return songRepository.save(song);
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
