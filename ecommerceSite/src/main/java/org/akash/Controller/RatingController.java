package org.akash.Controller;

import java.util.List;

import org.akash.exceptions.ProductException;
import org.akash.exceptions.UserException;
import org.akash.model.Rating;
import org.akash.model.User;
import org.akash.request.RatingRequest;
import org.akash.service.RatingService;
import org.akash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {


	@Autowired
	private UserService userService;
	
	@Autowired
	private RatingService ratingService;
	
	@PostMapping("/create")
	public ResponseEntity<Rating> createRating(@RequestBody RatingRequest req, 
			@RequestHeader("Authorization") String jwt) throws UserException, ProductException{
		
		User user = userService.findUserProfileByJwt(jwt);
		
		Rating rating = ratingService.createRating(req, user);
		
		return new ResponseEntity<Rating>(rating, HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Rating>> getProductRating(@PathVariable Long productId, 
			@RequestHeader("Authorization") String jwt) throws UserException, ProductException{
		
		
		User user = userService.findUserProfileByJwt(jwt);
		
		List<Rating> rating = ratingService.getProductsRating(productId);
		
		return new ResponseEntity<>(rating,HttpStatus.CREATED);
	}
	
	
	
}

















