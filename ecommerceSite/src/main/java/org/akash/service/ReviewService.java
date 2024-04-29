package org.akash.service;

import java.util.List;

import org.akash.exceptions.ProductException;
import org.akash.model.Review;
import org.akash.model.User;
import org.akash.request.ReviewRequest;

public interface ReviewService {

	public Review createReview(ReviewRequest req,  User user) throws ProductException;
	
	public List<Review> getAllReview(Long productId);
	
}
