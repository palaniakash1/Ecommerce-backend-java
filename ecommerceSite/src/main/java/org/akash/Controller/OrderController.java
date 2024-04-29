package org.akash.Controller;

import java.util.List;

import org.akash.exceptions.OrderException;
import org.akash.exceptions.UserException;
import org.akash.model.Address;
import org.akash.model.Order;
import org.akash.model.User;
import org.akash.service.OrdeService;
import org.akash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrdeService ordeService;

	@Autowired
	private UserService userService;

	@PostMapping("/")
	public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress,
			@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfileByJwt(jwt);

		Order order = ordeService.createOrder(user, shippingAddress);

		System.out.println("Order" + order);

		return new ResponseEntity<Order>(order, HttpStatus.CREATED);

	}

	@GetMapping("/user")
	public ResponseEntity<List<Order>> userOrderHistory(
			@RequestHeader("Authorization") String jwt) throws UserException{
		
		User user = userService.findUserProfileByJwt(jwt);
		
		List<Order> order=ordeService.userOrderHistory(user.getId());
		
		return new ResponseEntity<List<Order>>(order, HttpStatus.CREATED);
		
		
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Order> findOrderById(@PathVariable("id") Long orderId,
			@RequestHeader("Authorization") String jwt) throws UserException, OrderException {

		User user = userService.findUserProfileByJwt(jwt);

		Order order = ordeService.findOrderById(orderId);

		return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
	}

}
