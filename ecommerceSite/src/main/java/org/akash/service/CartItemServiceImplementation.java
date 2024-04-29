package org.akash.service;

import java.util.Optional;

import org.akash.exceptions.CartItemException;
import org.akash.exceptions.UserException;
import org.akash.model.Cart;
import org.akash.model.CartItem;
import org.akash.model.Product;
import org.akash.model.User;
import org.akash.repository.cartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class CartItemServiceImplementation implements CartItemService {

	@Autowired
	private cartItemRepository cartItemRepository;

	@Autowired
	private UserService userService;

	@Override
	public CartItem createCartItem(CartItem cartItem) {
		// TODO Auto-generated method stub

		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
		cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPercent() * cartItem.getQuantity());

		CartItem createdCartItem = cartItemRepository.save(cartItem);

		return createdCartItem;
	}

	@Override
	public CartItem upateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
		// TODO Auto-generated method stub

		CartItem item = findCartItemById(id);
		User user = userService.findUserById(userId);

		if (user.getId().equals(userId)) {
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity() * item.getProduct().getPrice());
			item.setDiscountedPrice(item.getProduct().getDiscountedPrice() * item.getQuantity());
		}

		return cartItemRepository.save(item);
	}

	@Override
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
		// TODO Auto-generated method stub

		CartItem cartItem = cartItemRepository.isCartItemExist(cart, product, size, userId);

		return cartItem;
	}

	@Override
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
		// TODO Auto-generated method stub

		CartItem cartItem = findCartItemById(cartItemId);

		User user = userService.findUserById(cartItem.getUserId());

		User reqUser = userService.findUserById(userId);

		if (user.getId().equals(reqUser.getId())) {
			cartItemRepository.deleteById(cartItemId);
		} else {
			throw new UserException("you cannot remove other items");
		}

	}

	@Override
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {
		// TODO Auto-generated method stub

		Optional<CartItem> opt = cartItemRepository.findById(cartItemId);

		if (opt.isPresent()) {
			return opt.get();

		}

		throw new CartItemException("Cart item not found with id " + cartItemId);

	}

}
