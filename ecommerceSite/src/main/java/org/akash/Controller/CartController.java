package org.akash.Controller;

import org.akash.exceptions.ProductException;
import org.akash.exceptions.UserException;
import org.akash.model.Cart;
import org.akash.model.User;
import org.akash.request.AddItemRequest;
import org.akash.response.ApiResponse;
import org.akash.service.CartService;
import org.akash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private  CartService cartService;

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfileByJwt(jwt);

		Cart cart = cartService.findUserCart(user.getId());

		return new ResponseEntity<Cart>(cart, HttpStatus.OK);

	}

	@PutMapping("/add")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req,
			@RequestHeader("Authorization") String jwt) throws UserException, ProductException {

		User user = userService.findUserProfileByJwt(jwt);

		cartService.addCartItem(user.getId(), req);

		ApiResponse res = new ApiResponse();
		res.setMessage("Item Added To Cart");
		res.setStatus(true);

		return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);

	}

}
