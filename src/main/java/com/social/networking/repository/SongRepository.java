package com.social.networking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.social.networking.entity.Song;

public interface SongRepository extends JpaRepository<Song, Integer> {
	public Optional<Song> findBySongName(String songName);
}
