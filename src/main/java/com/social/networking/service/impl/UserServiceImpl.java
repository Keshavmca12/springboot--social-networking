package com.social.networking.service.impl;

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
import com.social.networking.entity.User;
import com.social.networking.entity.UserPlaylist;
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
	private UserPlaylistRepository userPlaylistRepository;

	@Override
	public Set<String> getPlaylist(String userName) throws ApplicationException {
		LOGGER.info("getUserPlaylist : " + userName);
		Optional<User> user = userRepository.findByUserName(userName);

		if (!user.isPresent()) {
			throw new ApplicationException("User does not exist");
		}

		/***
		 * If user have followed multiple artist with that contains same song then only
		 * one will be added to user playlist
		 */
		Set<String> songs = new HashSet<>();
		List<UserPlaylist> userPlaylist = userPlaylistRepository.findByUserId(user.get().getUserId());
		for (UserPlaylist song : userPlaylist) {
			songs.add(song.getSongName());
		}
		return songs;
	}

}
