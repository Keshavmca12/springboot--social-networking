package com.socail.networking.util;

import java.util.stream.Collectors;

import com.social.networking.constant.AppContsants;
import com.social.networking.dto.FollowArtistDTO;
import com.social.networking.dto.ResponseDTO;

public class AppUtil {
	public static ResponseDTO getReposnseFailure() {
		return new ResponseDTO(AppContsants.reponseFailureStatus, AppContsants.reponseFailureMessage);
	}
	
	public static ResponseDTO getPublishReposnse() {
		return new ResponseDTO(AppContsants.reponseSuccessStatus, "song published against artists");
	}
	
	public static ResponseDTO getFollowReposnse(FollowArtistDTO artistDTO) {
		String listString = artistDTO.getArtist().stream().map(Object::toString)
                .collect(Collectors.joining(", "));

		String message = String.format(
				"%s started following %s.",
				artistDTO.getUser(), listString);
		return new ResponseDTO(AppContsants.reponseSuccessStatus, message);
	}
	
	
	public static ResponseDTO getUnollowReposnse(FollowArtistDTO artistDTO) {
		String listString = artistDTO.getArtist().stream().map(Object::toString)
                .collect(Collectors.joining(", "));
		String message = String.format(
				"%s stopped following %s.",
				artistDTO.getUser(), listString);
		return new ResponseDTO(AppContsants.reponseSuccessStatus, message);
	}

}
