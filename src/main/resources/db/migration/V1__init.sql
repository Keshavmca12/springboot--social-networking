CREATE TABLE `artist` (
  `artist_id` int NOT NULL AUTO_INCREMENT,
  `artist_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`artist_id`),
  UNIQUE KEY `UK_h269s1w9aa7yf6whby2jwbix6` (`artist_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_lqjrcobrh9jc8wpcar64q1bfh` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `song` (
  `song_id` int NOT NULL AUTO_INCREMENT,
  `song_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`song_id`),
  UNIQUE KEY `UK_c67h3aakc5orlnrnrwk8jr0lj` (`song_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `artist_song_mapping` (
  `artist_id` int NOT NULL,
  `song_id` int NOT NULL,
  PRIMARY KEY (`artist_id`,`song_id`),
  KEY `FK8ta2pw0ylceiukxs7mlvum73j` (`song_id`),
  CONSTRAINT `FK8ta2pw0ylceiukxs7mlvum73j` FOREIGN KEY (`song_id`) REFERENCES `song` (`song_id`),
  CONSTRAINT `FKhlvbaqbrcdvx9g9aygor93eyv` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`artist_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `user_artist_mapping` (
  `user_id` int NOT NULL,
  `artist_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`artist_id`),
  KEY `FKh7x0711us665yu6fhtpkfgw8t` (`artist_id`),
  CONSTRAINT `FKh7x0711us665yu6fhtpkfgw8t` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`artist_id`),
  CONSTRAINT `FKkacogucxa86ddks4nn3kfj426` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_playlist` (
  `playlist_id` int NOT NULL AUTO_INCREMENT,
  `artist_name` varchar(255) DEFAULT NULL,
  `song_name` varchar(255) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`playlist_id`),
  UNIQUE KEY `UKi9cw5amx9vms8yrvkuuic7nhm` (`user_id`,`artist_name`,`song_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
