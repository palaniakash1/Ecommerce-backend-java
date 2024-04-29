package org.akash.service;

import java.util.List;

import org.akash.exceptions.ProductException;
import org.akash.model.Rating;
import org.akash.model.User;
import org.akash.request.RatingRequest;

public interface RatingService {

	public Rating createRating(RatingRequest req, User user) throws ProductException;
	
	public List<Rating> getProductsRating(Long productId);
	
	
	
}
