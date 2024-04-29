package org.akash.repository;

import java.util.List;

import org.akash.model.Product;
import org.akash.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review , Long>{

	@Query("SELECT r FROM Review r WHERE r.product.id=:productId")
	public List<Review> getAllProductsReview(@Param("productId") Long productId);
}
