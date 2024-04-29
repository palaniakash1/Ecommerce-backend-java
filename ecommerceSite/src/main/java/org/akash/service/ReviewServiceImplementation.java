package org.akash.service;

import java.time.LocalDateTime;
import java.util.List;

import org.akash.exceptions.ProductException;
import org.akash.model.Product;
import org.akash.model.Review;
import org.akash.model.User;
import org.akash.repository.ProductRepository;
import org.akash.repository.ReviewRepository;
import org.akash.request.ReviewRequest;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class ReviewServiceImplementation implements ReviewService{

	private ReviewRepository reviewRepository;
	private ProductService ProductService;
	private ProductRepository productRepository;
	
	
	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException {
		// TODO Auto-generated method stub

		Product product= ProductService.findProductById(req.getProductId());
		
		Review review = new Review();
		 review.setUser(user);
		 review.setProduct(product);
		 review.setReview(req.getReview());
		 review.setCreatedAt(LocalDateTime.now());
		 
		 
		return reviewRepository.save(review);		
		
	}

	@Override
	public List<Review> getAllReview(Long productId) {
		// TODO Auto-generated method stub
		
		reviewRepository.getAllProductsReview(productId);
		return null;
	}
	

}
