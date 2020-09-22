package com.social.networking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.social.networking.entity.UserPlaylist;

public interface UserPlaylistRepository extends JpaRepository<UserPlaylist, Integer> {
	@Modifying
	@Query(value = "delete from user_playlist where user_id = ?1 and artist_name= ?2", nativeQuery = true)
	void removeArtistSongFromPlayList(int playlistId, String artistName);

	List<UserPlaylist> findByUserId(int userId);
	
	@Query(value = "select count(song_name) as popular_song, song_name from user_playlist group by song_name order by popular_song desc limit 1", nativeQuery = true)
	List<Object[]> getMostPopularSong();
	
}
