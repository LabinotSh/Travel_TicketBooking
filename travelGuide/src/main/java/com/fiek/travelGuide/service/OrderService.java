package com.fiek.travelGuide.service;

import com.fiek.travelGuide.domain.*;

public interface OrderService {

    Order createOrder(ShoppingCart shoppingCart,
                      ShippingAddress shippingAddress,
                      BillingAddress billingAddress,
                      Payment payment,
                      String shippingMethod, User user);

    Order getOne(Long id);
}
