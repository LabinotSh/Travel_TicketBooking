package com.fiek.travelGuide.service;

import com.fiek.travelGuide.domain.*;
import com.fiek.travelGuide.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemService cartItemService;

    @Override
    public synchronized Order createOrder(ShoppingCart shoppingCart, ShippingAddress shippingAddress, BillingAddress billingAddress, Payment payment, String shippingMethod, User user) {

       Order order = new Order();
       order.setBilingAddress(billingAddress);
       order.setOrderStatus("created");
       order.setPayment(payment);
       order.setShippingAddress(shippingAddress);
       order.setShippingMethod(shippingMethod);

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        for(CartItem cartItem : cartItemList){
            Location location = cartItem.getLocation();
            cartItem.setOrder(order);
            location.setNrOfTickets(location.getNrOfTickets() - cartItem.getQty());
        }
        order.setCartItemList(cartItemList);
        order.setOrderDate(Calendar.getInstance().getTime());
        order.setOrderTotal(shoppingCart.getGrandTotal());

        shippingAddress.setOrder(order);
        billingAddress.setOrder(order);
        payment.setOrder(order);
        order.setUser(user);

        order = orderRepository.save(order);

        return order;
    }

    @Override
    public Order getOne(Long id) {
        return orderRepository.getOne(id);
    }
}
