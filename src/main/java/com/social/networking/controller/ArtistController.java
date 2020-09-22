package com.social.networking.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socail.networking.exception.ApplicationException;
import com.socail.networking.util.AppUtil;
import com.social.networking.dto.FollowArtistDTO;
import com.social.networking.dto.ResponseDTO;
import com.social.networking.entity.User;
import com.social.networking.service.ArtistService;

@RestController
@RequestMapping("wynk")
public class ArtistController {

	@Autowired
	private ArtistService artistService;

	@PostMapping(value = "/follow", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> followArtist(@RequestBody FollowArtistDTO followDTO)
			throws ApplicationException {
		User user = artistService.followArtist(followDTO);
		if (null != user) {
			return ResponseEntity.ok().body(AppUtil.getFollowReposnse(followDTO));
		}
		return ResponseEntity.badRequest().body(AppUtil.getReposnseFailure());
	}

	@PostMapping(value = "/unfollow", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> unfollowArtist(@RequestBody FollowArtistDTO followDTO)
			throws ApplicationException {
		User result = artistService.unfollowArtist(followDTO);
		if (null != result) {
			return ResponseEntity.ok().body(AppUtil.getUnollowReposnse(followDTO));
		}
		return ResponseEntity.badRequest().body(AppUtil.getReposnseFailure());
	}

	@GetMapping(value = "/popular/artist", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getMostPopularArtist() throws ApplicationException {
		String artist = artistService.getMostPopularArtist();
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("artist", artist);
		if (null != artist) {
			return ResponseEntity.ok().body(resultMap);
		}
		return ResponseEntity.badRequest().body(AppUtil.getReposnseFailure());
	}

}
