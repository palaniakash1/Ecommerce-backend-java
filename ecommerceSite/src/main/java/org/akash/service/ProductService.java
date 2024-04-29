package org.akash.service;

import java.util.List;

import org.akash.exceptions.ProductException;
import org.akash.model.Product;
import org.akash.request.CreateProductRequest;
import org.springframework.data.domain.Page;

public interface ProductService {

	public Product createProduct(CreateProductRequest req);

	public String deleteProduct(Long productId) throws ProductException;

	public Product updateProduct(Long productId, Product req) throws ProductException;

	public Product findProductById(Long id) throws ProductException;

	public List<Product> findProductByCategory(String category);

	public List<Product> findAllProducts();
	
	public Page<Product> getAllProduct(String category, List<String> color, List<String> size,
							Integer minPrice,Integer maxPrice, Integer minDiscount, String sort,
							String stock, Integer pageNumber, Integer pageSize);
}
