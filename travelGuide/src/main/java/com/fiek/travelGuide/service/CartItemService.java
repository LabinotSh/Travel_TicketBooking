package com.fiek.travelGuide.service;

import com.fiek.travelGuide.domain.*;

import java.util.List;

public interface CartItemService {

    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

    CartItem updateCartItem(CartItem cartItem);

    CartItem addLocationToCartItem(Location location, User user, int qty);

    CartItem getOne(Long id);

    void removeById(Long id);

    CartItem save(CartItem cartItem);
}
