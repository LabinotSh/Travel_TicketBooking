package com.fiek.travelGuide.service;

import com.fiek.travelGuide.domain.*;
import com.fiek.travelGuide.repository.CartItemRepository;
import com.fiek.travelGuide.repository.LocationToCartItemRepository;
import com.fiek.travelGuide.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private LocationToCartItemRepository locationToCartItemRepository;

    @Override
    public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
        return cartItemRepository.findByShoppingCart(shoppingCart);
    }

    @Override
    public CartItem updateCartItem(CartItem cartItem) {
        BigDecimal bigDecimal = new BigDecimal(cartItem.getLocation().
                getBookingPrice()).multiply(new BigDecimal(cartItem.getQty()));

        bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        cartItem.setSubTotal(bigDecimal);

        cartItemRepository.save(cartItem);

        return  cartItem;

    }

    @Override
    public CartItem addLocationToCartItem(Location location, User user, int qty, Date bookingDate,Ticket ticket) {

        List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());

//        for(CartItem cartItem : cartItemList){
//            if(location.getId() == cartItem.getLocation().getId() && location.getBookingDate() != bookingDate){
//
//                cartItem.setQty(cartItem.getQty()+qty);
//                cartItem.setSubTotal(new BigDecimal(location.getBookingPrice()).multiply(new BigDecimal(qty)));
//                cartItemRepository.save(cartItem);
//                return cartItem;
//            }
//        }
//        if(ticket.getBookingDate() != bookingDate) {
//            ticket = ticketRepository.findByLocation(location);
//            ticket = ticketRepository.findByDate(bookingDate);
//    }

        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(user.getShoppingCart());
        cartItem.setLocation(location);
        cartItem.setTicket(ticket);
        //Added


        cartItem.setQty(qty);
        cartItem.setSubTotal(new BigDecimal(location.getBookingPrice()).multiply(new BigDecimal(qty)));
        cartItem = cartItemRepository.save(cartItem);


        LocationToCartItem locationToCartItem = new LocationToCartItem();
        locationToCartItem.setLocation(location);
        locationToCartItem.setCartItem(cartItem);
        locationToCartItemRepository.save(locationToCartItem);

        return cartItem;
    }


    @Override
    public CartItem getOne(Long id) {
        return cartItemRepository.getOne(id);
    }

    @Override
    public void removeById(Long id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItem> findByOrder(Order order) {
        return cartItemRepository.findByOrder(order);
    }

    @Override
    public List<CartItem> findAll() {
        return (List<CartItem>) cartItemRepository.findAll();
    }

}
