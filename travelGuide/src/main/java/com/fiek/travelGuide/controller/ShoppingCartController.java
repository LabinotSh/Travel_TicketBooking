package com.fiek.travelGuide.controller;


import com.fiek.travelGuide.domain.*;
import com.fiek.travelGuide.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping(value = {"/shoppingCart"})
public class ShoppingCartController {


    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private LocationService locationService;

    //Added
    @Autowired
    private TicketService ticketService;


    @RequestMapping(value = {"/cart"})
    public String shoppingCart(@ModelAttribute("ticket") Ticket ticket,
                               @ModelAttribute("location") Location location,
                               Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        if(shoppingCart == null) {
            model.addAttribute("user", user);
            model.addAttribute("emptyCart", true);
            return "shoppingCart";
        }
        shoppingCartService.updateShoppingCart(shoppingCart);

        model.addAttribute("ticket",ticket);
        model.addAttribute("user",user);
        model.addAttribute("cartItemList",cartItemList);
        model.addAttribute("shoppingCart",shoppingCart);

        return "shoppingCart";
    }

    ////????????
    ArrayList<Date> dateArrayList = new ArrayList<>();

    @RequestMapping(value = {"/addItem"})
    public String addItem(@ModelAttribute("location") Location location,
                          @ModelAttribute("qty") String qty,
                          @ModelAttribute("bookingDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date bookingDate,
//                          @ModelAttribute("ticket") Ticket ticket,
                          Model model, Principal principal){

        User user = userService.findByUsername(principal.getName());

        location = locationService.getOne(location.getId());

        //Added
        Ticket ticket = new Ticket(qty);
        ticket.setLocation(location);
        ticket.setBookingPrice(location.getBookingPrice());
        ticket.setUser(user);

        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
//            location.setBookingDate(simpleDateFormat.parse(String.valueOf(bookingDate)));
            ticket.setBookingDate(simpleDateFormat.parse(String.valueOf(bookingDate)));
//            tickets.add(ticket);
//            location.setTicket(ticket);
            locationService.save(location);
            ticketService.save(ticket);
        }catch (ParseException e) {
            e.printStackTrace();
        }

        //Added
        dateArrayList.add(bookingDate);
        System.out.println("DATEEE" + bookingDate);
        System.out.println("SETT"+ dateArrayList);

        if(Integer.parseInt(qty) > location.getNrOfTickets()){
            model.addAttribute("notEnoughStock",true);
            return "forward:/locationDetails?id="+location.getId();
        }
        //Added ticket

        CartItem cartItem = cartItemService.addLocationToCartItem(location, user, Integer.parseInt(qty), bookingDate,ticket);
        model.addAttribute("addLocationSuccess", true);
        return "forward:/locationDetails?id=" + location.getId();
//
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }



    @RequestMapping(value={"/removeItem"})
    public String removeCartItem(
            @ModelAttribute("id") Long cartItemId,
            Principal principal,
            Model model
    ) {
        User user = userService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();

        CartItem cartItem = cartItemService.getOne(cartItemId);

        if(cartItem.getShoppingCart().getId() != shoppingCart.getId()){
            return "badRequestPage";
        }else {


            model.addAttribute("user", user);

            cartItemService.removeById(cartItem.getId());
            cartItem.getTicket().setBookingDate(null);

            List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
//            cartItemList = cartItemService.findAll();
            shoppingCartService.updateShoppingCart(shoppingCart);

            model.addAttribute("cartItemList",cartItemList);
            model.addAttribute("shoppingCart", shoppingCart);

            return "shoppingCart";
        }
    }


    @RequestMapping(value={"/updateCartItem"})
    public String updateCartItem(
            @ModelAttribute("id") Long cartItemId,
            @ModelAttribute("qty") int qty,
            Principal principal,
            Model model
    ) {
        User user = userService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        CartItem cartItem = cartItemService.getOne(cartItemId);
        cartItem.setQty(qty);
        cartItemService.updateCartItem(cartItem);

        shoppingCartService.updateShoppingCart(shoppingCart);

        model.addAttribute("user", user);
        model.addAttribute("cartItemList",cartItemList);
        model.addAttribute("shoppingCart", shoppingCart);

        return "forward:/shoppingCart/cart";

    }



}
