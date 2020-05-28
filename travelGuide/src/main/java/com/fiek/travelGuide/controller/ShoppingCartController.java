package com.fiek.travelGuide.controller;


import com.fiek.travelGuide.domain.*;
import com.fiek.travelGuide.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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


    @RequestMapping(value = {"/cart"})
    public String shoppingCart(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        if(shoppingCart == null){
            model.addAttribute("user",user);
            model.addAttribute("emptyCart",true);
            return "emptyShoppingCart";
        }

        shoppingCartService.updateShoppingCart(shoppingCart);

        model.addAttribute("user",user);
        model.addAttribute("cartItemList",cartItemList);
        model.addAttribute("shoppingCart",shoppingCart);

        return "shoppingCart";
    }

    ////????????

    @RequestMapping(value = {"/addItem"})
    public String addItem(@ModelAttribute("location") Location location,
                          @ModelAttribute("qty") String qty,
                          Model model, Principal principal){

        User user = userService.findByUsername(principal.getName());

        location = locationService.getOne(location.getId());

        if(Integer.parseInt(qty) > location.getNrOfTickets()){
            model.addAttribute("notEnoughStock",true);
            return "forward:/locationDetails?id="+location.getId();
        }
        CartItem cartItem = cartItemService.addLocationToCartItem(location,user,Integer.parseInt(qty));
        model.addAttribute("addLocationSuccess",true);
//        return "forward:/locationDetails?id="+location.getId();
        return "shoppingCart";

    }

    @RequestMapping(value={"/removeItem"})
    public String removeCartItem(
            @ModelAttribute("id") Long cartItemId,
            Principal principal,
            Model model
    ) {
        User user = userService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        CartItem cartItem = cartItemService.getOne(cartItemId);


        if(cartItem.getShoppingCart().getId() != shoppingCart.getId()){
            return "badRequestPage";
        }else {


            model.addAttribute("user", user);

            cartItemService.removeById(cartItemId);

            model.addAttribute("cartItemList",cartItemList);
            model.addAttribute("shoppingCart", shoppingCart);

            return "shoppingCart";
        }
    }


    @RequestMapping(value={"/updateCartItem"})
    public String updateCartItem(
            @ModelAttribute("id") Long cartItemId,
            Principal principal,
            Model model
    ) {
        User user = userService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        CartItem cartItem = cartItemService.getOne(cartItemId);


        shoppingCartService.updateShoppingCart(shoppingCart);

            model.addAttribute("user", user);
            model.addAttribute("cartItemList",cartItemList);
            model.addAttribute("shoppingCart", shoppingCart);

            return "shoppingCart";

    }



}
