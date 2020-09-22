package com.social.networking.dto;

import java.util.ArrayList;
import java.util.List;

public class FollowArtistDTO {

	private String user;
	private List<String> artist = new ArrayList<>();

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public List<String> getArtist() {
		return artist;
	}

	public void setArtist(List<String> artist) {
		this.artist = artist;
	}

}
