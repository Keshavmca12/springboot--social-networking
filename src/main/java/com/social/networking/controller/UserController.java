package com.social.networking.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.socail.networking.exception.ApplicationException;
import com.socail.networking.util.AppUtil;
import com.social.networking.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("wynk")
public class UserController {
	
	@Autowired
	private UserService userService;
	@ApiOperation(value="User Playlist", notes="List of songs")
	@GetMapping(value = "/playlist", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> publishSong(@RequestParam String user) throws ApplicationException {
		Set<String> songs = userService.getPlaylist(user);
		Map<String, Set<String>> resultMap = new HashMap<>();
		resultMap.put("songs", songs);
		if (null != songs) {
			return ResponseEntity.ok().body(resultMap);
		}
		return ResponseEntity.badRequest().body(AppUtil.getReposnseFailure());
	}
}
