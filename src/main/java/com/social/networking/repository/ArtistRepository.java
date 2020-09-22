package com.social.networking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.social.networking.entity.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
	public Optional<Artist> findByArtistName(String artistName);
			
	@Query(value = "select count(uam.user_id)as popular_artist,a.artist_name  from USER_ARTIST_MAPPING uam join artist a on uam.artist_id=a.artist_id group by uam.artist_id  order by popular_artist desc limit 1", nativeQuery = true)
	List<Object[]> getMostPopularArtist();
}
