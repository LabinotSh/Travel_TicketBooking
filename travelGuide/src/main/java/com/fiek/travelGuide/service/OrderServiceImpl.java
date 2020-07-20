package com.fiek.travelGuide.service;

import com.fiek.travelGuide.domain.*;
import com.fiek.travelGuide.repository.OrderRepository;
import com.fiek.travelGuide.repository.ShippingAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    //Added
    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    private CartItemService cartItemService;

    @Override
    @Transactional
    public synchronized Order createOrder(ShoppingCart shoppingCart, ShippingAddress shippingAddress, String shippingMethod, User user,Payment payment) {

       Order order = new Order();
//       order.setBilingAddress(billingAddress);
       order.setOrderStatus("created");
       //order.setPayment(payment);
       //order.setShippingAddress(shippingAddress);
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

//        shippingAddress.setOrder(order);
//        shippingAddress.setUser(user);
//        shippingAddressRepository.save(shippingAddress);
//        billingAddress.setOrder(order);
//        payment.setOrder(order);
        order.setUser(user);
        order = orderRepository.save(order);

        if(shippingAddress == null || order.getShippingAddress()== null || shippingMethod == null || payment == null) {
            order.setShippingAddress(shippingAddress);
            order.setShippingMethod(order.getShippingMethod());
            //order.setPayment(order.getPayment());
            order = orderRepository.save(order);
        }
        return order;
    }

    @Override
    public Order getOne(Long id) {
        return orderRepository.getOne(id);
    }
}
