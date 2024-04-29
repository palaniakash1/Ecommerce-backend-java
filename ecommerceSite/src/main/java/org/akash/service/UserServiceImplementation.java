package org.akash.service;

import java.util.Optional;

import org.akash.config.JwtProvider;
import org.akash.exceptions.UserException;
import org.akash.model.User;
import org.akash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceImplementation  implements UserService{

	@Autowired
	private UserRepository userRepository;
	private JwtProvider jwtProvider;
	
	
	@Override
	public User findUserById(Long userid) throws UserException {
		// TODO Auto-generated method stub
		
		Optional<User> user = userRepository.findById(userid);
		if (user.isPresent()) {
			return user.get();
		}
		
		
		throw new UserException("User Not Found With id "+ userid);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		// TODO Auto-generated method stub
		
		String email = jwtProvider.getEmailFromToken(jwt);
		
		User user = userRepository.findByEmail(email);
		
		if (user==null) {
			throw new UserException("User Not Found with email" + email);
		}
		
		
		return user;
	}

}
