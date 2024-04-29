package org.akash.service;

import org.akash.model.OrderItem;
import org.akash.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderItemServiceImplementation implements OrderItemService {

	@Autowired
	private OrderItemRepository orderItemRepository;
	
	
	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		return orderItemRepository.save(orderItem);
	}

}
