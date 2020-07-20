package com.fiek.travelGuide.service;

import com.fiek.travelGuide.domain.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface CartItemService {

    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

    CartItem updateCartItem(CartItem cartItem);

    CartItem addLocationToCartItem(Location location, User user, int qty, Date bookingDate,Ticket ticket);

    CartItem getOne(Long id);

    void removeById(Long id);

    CartItem save(CartItem cartItem);

    List<CartItem> findByOrder(Order order);

    List<CartItem> findAll();
}
