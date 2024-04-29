package org.akash.Controller;

import java.util.List;

import org.akash.exceptions.ProductException;
import org.akash.model.Product;
import org.akash.request.CreateProductRequest;
import org.akash.response.ApiResponse;
import org.akash.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin/products")
public class AdminProductController {

	
	@Autowired
	private ProductService productService;
	
	
	
	@PostMapping("/")
	public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req){
		
		Product product = productService.createProduct(req);
		
		return new ResponseEntity<Product>(product, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/{productId}/delete")
	public ResponseEntity<ApiResponse> deleteProduct(Long productId)throws ProductException{
		productService.deleteProduct(productId);
		
		ApiResponse res = new ApiResponse();
		res.setMessage("Product Deleted Successfully");
		res.setStatus(true);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	
	@GetMapping("/all")
	public ResponseEntity<List<Product>> findAllProduct(){
		
		List<Product> product = productService.findAllProducts();
		
		return new ResponseEntity<List<Product>>(product, HttpStatus.OK);
	}
	
	
	@PutMapping("/{product}/update")
	public ResponseEntity<Product> updateProduct(@RequestBody Product req,Long productId)
	throws ProductException{
		
		Product product= productService.updateProduct(productId, req);
		
		return new ResponseEntity<Product>(product, HttpStatus.CREATED);
	}
	
	@PostMapping("/creates")
	public ResponseEntity<ApiResponse> createMultipleProduct( CreateProductRequest[] req){
		
		for (CreateProductRequest product: req) {
			
			productService.createProduct(product);
			
		}
		ApiResponse res = new ApiResponse();
		
		res.setMessage("Products Created successfully");
		res.setStatus(true);
		
		return new ResponseEntity<ApiResponse>(res,HttpStatus.CREATED);
		
	}
	
}



















