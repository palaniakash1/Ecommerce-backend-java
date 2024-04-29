package org.akash.service;

import java.time.LocalDateTime;
import java.util.List;

import org.akash.exceptions.ProductException;
import org.akash.model.Product;
import org.akash.model.Rating;
import org.akash.model.User;
import org.akash.repository.RatingRepository;
import org.akash.request.RatingRequest;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RatingServiceImplementation implements RatingService{

	private RatingRepository ratingRepository;
	private ProductService productService;
	
	
	@Override
	public Rating createRating(RatingRequest req, User user) throws ProductException {
		// TODO Auto-generated method stub
		
		Product product = productService.findProductById(req.getProductId());
		Rating rating = new Rating();
		
		rating.setProduct(product);
		rating.setUser(user);
		rating.setRating(req.getRating());
		rating.setCreatedAt(LocalDateTime.now());
		
		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getProductsRating(Long productId) {
		// TODO Auto-generated method stub
		
		return ratingRepository.getAllProductsRating(productId);
	}

}
