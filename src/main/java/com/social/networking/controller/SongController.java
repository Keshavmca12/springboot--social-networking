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
import com.social.networking.dto.PublishSongDTO;
import com.social.networking.dto.ResponseDTO;
import com.social.networking.service.SongService;

@RestController
@RequestMapping("wynk")
public class SongController {
	
	@Autowired
	private SongService songService;
	
	@PostMapping(value = "/publish", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> publishSong(@RequestBody PublishSongDTO publishSongDTO) throws ApplicationException {
		boolean result = songService.publishSong(publishSongDTO);
		if (result) {
			return ResponseEntity.ok().body(AppUtil.getPublishReposnse());
		}
		return ResponseEntity.badRequest().body(AppUtil.getReposnseFailure());
	}
	
	@GetMapping(value = "/popular/song", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getMostPopularSong() throws ApplicationException {
		String song = songService.getMostPopularSong();
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("song", song);
		if (null != song) {
			return ResponseEntity.ok().body(resultMap);
		}
		return ResponseEntity.badRequest().body(AppUtil.getReposnseFailure());
	}
}
