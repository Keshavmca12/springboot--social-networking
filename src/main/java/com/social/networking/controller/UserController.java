package com.social.networking.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.socail.networking.exception.ApplicationException;
import com.socail.networking.util.AppUtil;
import com.social.networking.dto.FollowArtistDTO;
import com.social.networking.dto.PublishSongDTO;
import com.social.networking.dto.ResponseDTO;
import com.social.networking.entity.User;
import com.social.networking.service.UserService;

@RestController
@RequestMapping("wynk")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/follow", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> followArtist(@RequestBody FollowArtistDTO followDTO) throws ApplicationException {
		User user = userService.followArtist(followDTO);
		if (null != user) {
			return ResponseEntity.ok().body(AppUtil.getFollowReposnse(followDTO));
		}
		return ResponseEntity.badRequest().body(AppUtil.getReposnseFailure());
	}

	
	@PostMapping(value = "/unfollow", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> unfollowArtist(@RequestBody FollowArtistDTO followDTO) throws ApplicationException {
		User result = userService.unfollowArtist(followDTO);
		if (null != result) {
			return ResponseEntity.ok().body(AppUtil.getUnollowReposnse(followDTO));
		}
		return ResponseEntity.badRequest().body(AppUtil.getReposnseFailure());
	}

	
	@PostMapping(value = "/publish", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> publishSong(@RequestBody PublishSongDTO publishSongDTO) throws ApplicationException {
		boolean result = userService.publishSong(publishSongDTO);
		if (result) {
			return ResponseEntity.ok().body(AppUtil.getPublishReposnse());
		}
		return ResponseEntity.badRequest().body(AppUtil.getReposnseFailure());
	}
	
	@GetMapping(value = "/playlist",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> publishSong(@RequestParam String user) throws ApplicationException {
		Set<String> songs = userService.getPlaylist(user);
		Map<String, Set<String>> resultMap = new HashMap<>();
		resultMap.put("songs", songs);
		if (null != songs) {
			return ResponseEntity.ok().body(resultMap);
		}
		return ResponseEntity.badRequest().body(AppUtil.getReposnseFailure());
	}
	
	@GetMapping(value = "/popular/song", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getMostPopularSong() throws ApplicationException {
		String song = userService.getMostPopularSong();
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("song", song);
		if (null != song) {
			return ResponseEntity.ok().body(resultMap);
		}
		return ResponseEntity.badRequest().body(AppUtil.getReposnseFailure());
	}
	
	@GetMapping(value = "/popular/artist", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getMostPopularArtist() throws ApplicationException {
		String artist = userService.getMostPopularArtist();
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("artist", artist);
		if (null != artist) {
			return ResponseEntity.ok().body(resultMap);
		}
		return ResponseEntity.badRequest().body(AppUtil.getReposnseFailure());
	}
	
	@GetMapping(value = "/follow/count", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getArtistFollowerCount(@RequestParam String artist) throws ApplicationException {
		Integer count = userService.getArtistFollowerCount(artist);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("artist", artist);
		resultMap.put("count", count);
		if (null != count) {
			return ResponseEntity.ok().body(resultMap);
		}
		return ResponseEntity.badRequest().body(AppUtil.getReposnseFailure());
	}
	
	
	
	@GetMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> getStatus() {

		return ResponseEntity.ok().body(new ResponseDTO("Working", "Succcess"));
	}
}
