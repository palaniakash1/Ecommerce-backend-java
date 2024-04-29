package org.akash.repository;

import java.util.List;

import org.akash.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RatingRepository extends JpaRepository<Rating, Long>{

	
	@Query("SELECT r FROM Rating r WHERE r.product.id=:productId")
	public List<Rating> getAllProductsRating(@Param("productId") Long ProductId);
	
}
