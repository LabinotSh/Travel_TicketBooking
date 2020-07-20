package com.fiek.travelGuide.service;

import com.fiek.travelGuide.domain.*;

public interface OrderService {

    Order createOrder(ShoppingCart shoppingCart,
                      ShippingAddress shippingAddress,
                      String shippingMethod, User user,Payment payment);

    Order getOne(Long id);
}
