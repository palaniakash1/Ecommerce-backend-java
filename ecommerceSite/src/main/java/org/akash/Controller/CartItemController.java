package org.akash.Controller;

import java.security.DrbgParameters.Reseed;

import org.akash.exceptions.CartItemException;
import org.akash.exceptions.UserException;
import org.akash.model.CartItem;
import org.akash.model.User;
import org.akash.repository.cartItemRepository;
import org.akash.response.ApiResponse;
import org.akash.service.CartItemService;
import org.akash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class CartItemController {

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private UserService userService;

	public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartItemId,
			@RequestHeader("Authorization") String jwt) throws UserException, CartItemException {

		User user = userService.findUserProfileByJwt(jwt);

		cartItemService.removeCartItem(user.getId(), cartItemId);

		ApiResponse res = new ApiResponse();

		res.setMessage("Item deleted ");
		res.setStatus(true);

		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	public ResponseEntity<CartItem> updateCartItem(
			@RequestBody CartItem cartItem,@PathVariable Long cartItemId,
			@RequestHeader("Authorization") String jwt) throws UserException,CartItemException{
		
		User user = userService.findUserProfileByJwt(jwt);
		
		CartItem updatedCartItem = cartItemService.upateCartItem(user.getId(), cartItemId, cartItem);
				
		return new ResponseEntity<CartItem>(updatedCartItem,HttpStatus.OK);
	}

}
