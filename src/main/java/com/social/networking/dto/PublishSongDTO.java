package com.social.networking.dto;

import java.util.ArrayList;
import java.util.List;

public class PublishSongDTO {

	private String song;
	private List<String> artists = new ArrayList<>();

	public String getSong() {
		return song;
	}

	public void setSong(String song) {
		this.song = song;
	}

	public List<String> getArtists() {
		return artists;
	}

	public void setArtists(List<String> artists) {
		this.artists = artists;
	}

}
