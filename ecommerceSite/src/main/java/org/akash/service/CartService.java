package org.akash.service;

import org.akash.exceptions.ProductException;
import org.akash.model.Cart;
import org.akash.model.User;
import org.akash.request.AddItemRequest;

public interface CartService {

	public Cart createCart(User user);
	
	public String addCartItem(long userId, AddItemRequest req ) throws ProductException;
	
	public Cart findUserCart(Long userId);
}
