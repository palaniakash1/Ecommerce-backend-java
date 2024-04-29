package org.akash.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.akash.exceptions.ProductException;
import org.akash.model.Category;
import org.akash.model.Product;
import org.akash.repository.CategoryRepository;
import org.akash.repository.ProductRepository;
import org.akash.request.CreateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ProductServiceImplementation implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	private UserService userService;
	private CategoryRepository categoryRepository;

	@Override
	public Product createProduct(CreateProductRequest req) {

		// first level category
		Category firstLevel = categoryRepository.findByName(req.getFirstLevelCategory());

		if (firstLevel == null) {

			Category firstLevelCategory = new Category();

			firstLevelCategory.setName(req.getFirstLevelCategory());
			firstLevelCategory.setLevel(1);

			firstLevel = categoryRepository.save(firstLevelCategory);

		}

		// second level category
		Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLevelCategory(),
				firstLevel.getName());

		if (secondLevel == null) {

			Category secondLevelCategory = new Category();

			secondLevelCategory.setName(req.getSecondLevelCategory());
			secondLevelCategory.setParentCategory(firstLevel);
			secondLevelCategory.setLevel(2);

			secondLevel = categoryRepository.save(secondLevelCategory);

		}

		// third level category
		Category thirdLevel = categoryRepository.findByNameAndParent(req.getThirdLevelCategory(),
				secondLevel.getName());

		if (thirdLevel == null) {

			Category thirdLevelCategory = new Category();

			thirdLevelCategory.setName(req.getThirdLevelCategory());
			thirdLevelCategory.setParentCategory(secondLevel);
			thirdLevelCategory.setLevel(3);

			thirdLevel = categoryRepository.save(thirdLevelCategory);

		}

		Product product = new Product();

		product.setTitle(req.getTitle());
		product.setColor(req.getColor());
		product.setDescription(req.getDescription());
		product.setDiscountedPrice(req.getDiscountedPrice());
		product.setDiscountedPercent(req.getDiscountedPercent());
		product.setImageUrl(req.getImageUrl());
		product.setBrand(req.getBrand());
		product.setPrice(req.getPrice());
		product.setSize(req.getSize());
		product.setQuantity(req.getQuanitiy());
		product.setCategory(thirdLevel);
		product.setCreatedAt(LocalDateTime.now());

		Product savedproduct = productRepository.save(product);

		return savedproduct;
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {

		Product product = findProductById(productId);

		product.getSize().clear();
		productRepository.delete(product);

		return "Product Deleted Successfully";
	}

	@Override
	public Product updateProduct(Long productId, Product req) throws ProductException {

		Product product = findProductById(productId);

		if (req.getQuantity() != 0) {

			product.setQuantity(req.getQuantity());
		}

		return productRepository.save(product);
	}

	@Override
	public Product findProductById(Long id) throws ProductException {
		// TODO Auto-generated method stub

		Optional<Product> opt = productRepository.findById(id);

		if (opt.isPresent()) {
			return opt.get();
		}
		throw new ProductException("Product Not Found With id " + id);
	}

	@Override
	public List<Product> findProductByCategory(String category) {
		// TODO Auto-generated method stub
		return productRepository.findByCategoryName(category);
	}

	@Override
	public Page<Product> getAllProduct(String category, List<String> color, List<String> size, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub

		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);

		if (!color.isEmpty()) {
			products = products.stream().filter(p -> color.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
					.collect(Collectors.toList());
		}

		if (stock != null) {
			if (stock.equals("in_stock")) {
				products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
			}
			else if(stock.equals("out_of_stock")) {
				products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
					
			}

		}
		
		int startIndex=(int)pageable.getOffset();
		int endIndex = Math.min(startIndex+pageable.getPageSize(), products.size());
		
		List<Product> pageContent = products.subList(startIndex, endIndex);
		
		Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable,products.size());
		

		return filteredProducts;
	}

	@Override
	public List<Product> findAllProducts() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}

}
