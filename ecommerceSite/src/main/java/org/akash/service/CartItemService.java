package org.akash.service;

import org.akash.exceptions.CartItemException;
import org.akash.exceptions.UserException;
import org.akash.model.Cart;
import org.akash.model.CartItem;
import org.akash.model.Product;

public interface CartItemService {

	public CartItem createCartItem(CartItem cartItem);

	public CartItem upateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;

	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;

	public CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
