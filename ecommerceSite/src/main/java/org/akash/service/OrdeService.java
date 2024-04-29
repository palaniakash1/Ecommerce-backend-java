package org.akash.service;

import java.util.List;

import org.akash.exceptions.OrderException;
import org.akash.model.Address;
import org.akash.model.Order;
import org.akash.model.User;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
public interface OrdeService {

	public Order createOrder(User user, Address shippingAddress);

	public Order findOrderById(long orderId) throws OrderException;

	public List<Order> userOrderHistory(long orderId);

	public Order placedOrder(long orderId) throws OrderException;

	public Order confirmedOrder(long orderId) throws OrderException;

	public Order shippedOrder(long orderId) throws OrderException;

	public Order deliveredOrder(long orderId) throws OrderException;

	public Order cancelledOrder(long orderId) throws OrderException;

	public List<Order> getAllOrders();

	public void deleteOrder(long orderId) throws OrderException;

}
