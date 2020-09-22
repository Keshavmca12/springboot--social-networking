package com.social.networking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name ="USER_PLAYLIST")
@Table(uniqueConstraints=
@UniqueConstraint(columnNames={"USER_ID", "ARTIST_NAME", "SONG_NAME"}))
public class UserPlaylist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PLAYLIST_ID")
	private Integer playlistId;

	@Column(name = "USER_ID")
	private Integer userId;

	@Column(name = "SONG_NAME")
	private String songName;

	@Column(name = "ARTIST_NAME")
	private String artistName;

	public Integer getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(Integer playlistId) {
		this.playlistId = playlistId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

}
