package org.akash.service;

import org.akash.exceptions.UserException;
import org.akash.model.User;

public interface UserService {

	public User findUserById(Long userid) throws UserException; 

	public User findUserProfileByJwt(String jwt) throws UserException; 
	
	
}
