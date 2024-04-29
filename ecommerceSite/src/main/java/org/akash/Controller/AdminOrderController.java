package org.akash.Controller;

import java.util.List;

import org.akash.exceptions.OrderException;
import org.akash.model.Order;
import org.akash.response.ApiResponse;
import org.akash.service.OrdeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

	@Autowired
	private OrdeService orderService;

	@GetMapping("/")
	public ResponseEntity<List<Order>> getAllHandler() {
		List<Order> orders = orderService.getAllOrders();

		return new ResponseEntity<List<Order>>(orders, HttpStatus.ACCEPTED);

	}

	@PutMapping("/{orderId}/confirmed")
	public ResponseEntity<Order> confirmedOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {

		Order order = orderService.confirmedOrder(orderId);

		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@PutMapping("/{orderId}/ship")
	public ResponseEntity<Order> shippedOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {

		Order order = orderService.shippedOrder(orderId);

		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@PutMapping("/{orderId}/deliver")
	public ResponseEntity<Order> deliverOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {

		Order order = orderService.deliveredOrder(orderId);

		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<Order> cancelledOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {

		Order order = orderService.cancelledOrder(orderId);

		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@DeleteMapping("/{orderId}/delete")
	public ResponseEntity<ApiResponse> deleteOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {
		
		orderService.deleteOrder(orderId);

		ApiResponse res = new ApiResponse();
		res.setMessage("Order Deleted Successfully");
		res.setStatus(true);
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
