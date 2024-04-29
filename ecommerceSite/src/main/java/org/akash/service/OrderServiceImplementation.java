package org.akash.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.akash.exceptions.OrderException;
import org.akash.model.Address;
import org.akash.model.Cart;
import org.akash.model.CartItem;
import org.akash.model.Order;
import org.akash.model.OrderItem;
import org.akash.model.User;
import org.akash.repository.AddressRepository;
import org.akash.repository.CartRepository;
import org.akash.repository.OrderItemRepository;
import org.akash.repository.OrderRepository;
import org.akash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class OrderServiceImplementation implements OrdeService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CartService cartItemService;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderItemService orderItemService;

	@Override
	public Order createOrder(User user, Address shippingAddress) {

		shippingAddress.setUser(user);

		Address address = addressRepository.save(shippingAddress);

		user.getAddress().add(address);
		userRepository.save(user);

		Cart cart = cartItemService.findUserCart(user.getId());

		List<OrderItem> orderItems = new ArrayList<>();

		for (CartItem item : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();

			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			orderItem.setDiscountedPrice(item.getDiscountedPrice());

			OrderItem createdOrderItem = orderItemRepository.save(orderItem);

			orderItems.add(createdOrderItem);
		}

		Order createdOrder = new Order();

		createdOrder.setUser(user);
		createdOrder.setOrderItems(orderItems);
		createdOrder.setTotalPrice(cart.getTotalPrice());
		createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountPrice());
		createdOrder.setDiscount(cart.getDiscount());
		createdOrder.setTotalItem(cart.getTotalItem());

		createdOrder.setShippingAddress(address);
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setOrderStatus("PENDING");
		createdOrder.getPaymentDetails().setStatus("PENDING");
		createdOrder.setCreatedAt(LocalDateTime.now());

		Order savedOrder = orderRepository.save(createdOrder);

		for (OrderItem item : orderItems) {
			item.setOrder(savedOrder);
			orderItemRepository.save(item);
		}
		;

		return savedOrder;
	}

	@Override
	public Order placedOrder(long orderId) throws OrderException {
		// TODO Auto-generated method stub

		Order order = findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setStatus("COMPLETED");

		return order;

	}

	@Override
	public Order confirmedOrder(long orderId) throws OrderException {
		// TODO Auto-generated method stub

		Order order = findOrderById(orderId);
		order.setOrderStatus("CONFIRMED");

		return orderRepository.save(order);
	}

	@Override
	public Order shippedOrder(long orderId) throws OrderException {
		// TODO Auto-generated method stub
		Order order = findOrderById(orderId);
		order.setOrderStatus("SHIPPED");

		return orderRepository.save(order);
	}

	@Override
	public Order deliveredOrder(long orderId) throws OrderException {
		// TODO Auto-generated method stub

		Order order = findOrderById(orderId);
		order.setOrderStatus("DELIVERED");

		return orderRepository.save(order);
	}

	@Override
	public Order cancelledOrder(long orderId) throws OrderException {
		// TODO Auto-generated method stub

		Order order = findOrderById(orderId);
		order.setOrderStatus("CANCELLED");

		return orderRepository.save(order);

	}

	@Override
	public Order findOrderById(long orderId) throws OrderException {
		// TODO Auto-generated method stub

		Optional<Order> opt = orderRepository.findById(orderId);

		if (opt.isPresent()) {
			return opt.get();
		}

		throw new OrderException("Order does not exist with id " + orderId);

	}

	@Override
	public List<Order> userOrderHistory(long userId) {
		// TODO Auto-generated method stub

		List<Order> order = orderRepository.getUserOrders(userId);

		return order;
	}

	@Override
	public List<Order> getAllOrders() {
		// TODO Auto-generated method stub
		return orderRepository.findAll();
	}

	@Override
	public void deleteOrder(long orderId) throws OrderException {
		// TODO Auto-generated method stub

		Order order = findOrderById(orderId);

		orderRepository.deleteById(orderId);

	}

}
