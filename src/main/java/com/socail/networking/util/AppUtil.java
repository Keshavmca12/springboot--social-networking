package com.socail.networking.util;

import java.util.stream.Collectors;

import com.social.networking.constant.ApplicationContsants;
import com.social.networking.dto.FollowArtistDTO;
import com.social.networking.dto.ResponseDTO;

public class AppUtil {
	public static ResponseDTO getReposnseFailure() {
		return new ResponseDTO(ApplicationContsants.reponseFailureStatus, ApplicationContsants.reponseFailureMessage);
	}

	public static ResponseDTO getReposnseFailure(String message) {
		return new ResponseDTO(ApplicationContsants.reponseFailureStatus, message);
	}
	
	public static ResponseDTO getPublishReposnse() {
		return new ResponseDTO(ApplicationContsants.reponseSuccessStatus, "song published against artists");
	}
	
	public static ResponseDTO getFollowReposnse(FollowArtistDTO artistDTO) {
		String listString = artistDTO.getArtist().stream().map(Object::toString)
                .collect(Collectors.joining(", "));

		String message = String.format(
				"%s started following %s.",
				artistDTO.getUser(), listString);
		return new ResponseDTO(ApplicationContsants.reponseSuccessStatus, message);
	}
	
	
	public static ResponseDTO getUnollowReposnse(FollowArtistDTO artistDTO) {
		String listString = artistDTO.getArtist().stream().map(Object::toString)
                .collect(Collectors.joining(", "));
		String message = String.format(
				"%s stopped following %s.",
				artistDTO.getUser(), listString);
		return new ResponseDTO(ApplicationContsants.reponseSuccessStatus, message);
	}

}
