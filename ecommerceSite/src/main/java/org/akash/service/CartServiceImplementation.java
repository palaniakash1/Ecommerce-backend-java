package org.akash.service;

import org.akash.exceptions.ProductException;
import org.akash.model.Cart;
import org.akash.model.CartItem;
import org.akash.model.Product;
import org.akash.model.User;
import org.akash.repository.CartRepository;
import org.akash.request.AddItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CartServiceImplementation implements CartService {

	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductService productService;

	@Override
	public Cart createCart(User user) {
		// TODO Auto-generated method stub

		Cart cart = new Cart();
		cart.setUser(user);

		return cartRepository.save(cart);
	}

	@Override
	public String addCartItem(long userId, AddItemRequest req) throws ProductException {
		// TODO Auto-generated method stub

		Cart cart = cartRepository.findByUserId(userId);

		Product product = productService.findProductById(req.getProductId());

		CartItem isPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);

		if (isPresent == null) {
			CartItem item = new CartItem();
			item.setCart(cart);
			item.setProduct(product);
			item.setQuantity(req.getQuantity());
			item.setUserId(userId);

			int price = req.getQuantity() * product.getDiscountedPercent();
			item.setPrice(price);
			item.setSize(req.getSize());

			CartItem createdCartItem = cartItemService.createCartItem(item);
			cart.getCartItems().add(createdCartItem);
		}

		return "Item Added To Your Cart";
	}

	@Override
	public Cart findUserCart(Long userId) {
		// TODO Auto-generated method stub

		Cart cart = cartRepository.findByUserId(userId);

		int totalPrice = 0;
		int totalDiscountedPrice = 0;
		int totalItem = 0;

		for (CartItem cartItem : cart.getCartItems()) {
			totalPrice = totalPrice + cartItem.getPrice();
			totalDiscountedPrice = totalDiscountedPrice + cartItem.getDiscountedPrice();
			totalItem = totalItem + cartItem.getQuantity();

		}

		cart.setTotalPrice(totalPrice);
		cart.setTotalDiscountPrice(totalDiscountedPrice);
		cart.setTotalItem(totalItem);
		cart.setDiscount(totalPrice - totalDiscountedPrice);

		return cartRepository.save(cart);
	}

}
