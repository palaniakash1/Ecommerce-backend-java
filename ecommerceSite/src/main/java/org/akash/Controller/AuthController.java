package org.akash.Controller;

import org.akash.config.JwtProvider;
import org.akash.exceptions.UserException;
import org.akash.model.Cart;
import org.akash.model.User;
import org.akash.repository.UserRepository;
import org.akash.request.LoginRequest;
import org.akash.response.AuthResponse;
import org.akash.service.CartService;
import org.akash.service.CustomUserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomUserServiceImplementation customUserServiceImplementation;

	@Autowired
	private CartService cartService;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {

		String email = user.getEmail();
		String password = user.getPassword();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();

		User isEmailExist = userRepository.findByEmail(email);

		if (isEmailExist != null) {
			throw new UserException("This Email Is Already Registered With Another Account");
		}

		User createduUser = new User();
		createduUser.setEmail(email);
		createduUser.setPassword(passwordEncoder.encode(password));
		createduUser.setFirstName(firstName);
		createduUser.setLastName(lastName);

		User savedUser = userRepository.save(createduUser);
		Cart cart = cartService.createCart(savedUser);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),
				savedUser.getPassword());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		AuthResponse authResponse = new AuthResponse(token, "SignUp Success");

		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);

	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest) {

		String userName = loginRequest.getEmail();
		String password = loginRequest.getPassword();

		Authentication authentication = authenticate(userName, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		AuthResponse authResponse = new AuthResponse(token, "SignIn Success");

		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
	}

	private Authentication authenticate(String userName, String password) {

		UserDetails userDetails = customUserServiceImplementation.loadUserByUsername(userName);

		if (userDetails == null) {
			throw new BadCredentialsException("Invalid UserName");
		}

		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password");
		}

		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}
