package com.social.networking.service;

import java.util.Set;

import com.socail.networking.exception.ApplicationException;

public interface UserService {

	Set<String> getPlaylist(String user) throws ApplicationException;

}
