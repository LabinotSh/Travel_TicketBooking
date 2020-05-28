package com.fiek.travelGuide.controller;

import com.fiek.travelGuide.domain.*;
import com.fiek.travelGuide.service.*;
import com.fiek.travelGuide.utility.MailConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Controller
public class CheckoutController {

    private ShippingAddress shippingAddress = new ShippingAddress();
    private BillingAddress billingAddress = new BillingAddress();
    private Payment payment = new Payment();

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConstructor mailConstructor;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ShippingAddressService shippingAddressService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private BillingAddressService billingAddressService;

    @Autowired
    private UserShippingService userShippingService;

    @Autowired
    private UserPaymentService userPaymentService;

   @Autowired
   private ShoppingCartService shoppingCartService;

   @Autowired
   private OrderService orderService;


    @RequestMapping(value = {"/checkout"})
    public String checkout(@RequestParam("id") Long cartId,
                           @RequestParam(value = "missingRequiredField",required = false) boolean missingRequiredField,
                           Model model, Principal principal){

        User user = userService.findByUsername(principal.getName());

        if(cartId != user.getShoppingCart().getId()){
            return "badRequestPage";
        }

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

        if(cartItemList.size() == 0){
            model.addAttribute("emptyCart",true);
            return "forward:/shoppingCart/cart";
        }

        for(CartItem cartItem : cartItemList){
            if(cartItem.getLocation().getNrOfTickets() < cartItem.getQty()){
                model.addAttribute("notEnoughStock",true);
                return "forward:/shoppingCart/cart";
            }
        }

        List<UserShipping> userShippingList = user.getUserShippingList();
        List<UserPayment> userPaymentList = user.getUserPaymentList();

        model.addAttribute("userShippingList",userShippingList);
        model.addAttribute("userPaymentList",userPaymentList);

        if(userPaymentList.size()==0){
            model.addAttribute("emptyPaymentList",true);
        }else{
            model.addAttribute("emptyPaymentList",false);
        }

        if(userShippingList.size()==0){
            model.addAttribute("emptyShippingList",true);
        }else{
            model.addAttribute("emptyShippingList",false);
        }

        ShoppingCart shoppingCart = user.getShoppingCart();

        for(UserShipping userShipping : userShippingList){
            if(userShipping.isUserShippingDefault()){
                shippingAddressService.setByUserShipping(userShipping,shippingAddress);
            }
        }

        for(UserPayment userPayment : userPaymentList){
            if(userPayment.isDefaultPayment()){
                paymentService.setByUserPayment(userPayment,payment);
                billingAddressService.setByUserBilling(userPayment.getUserBilling(),billingAddress);
            }
        }

        model.addAttribute("shippingAddress",shippingAddress);
        model.addAttribute("payment",payment);
        model.addAttribute("billingAddress",billingAddress);
        model.addAttribute("cartItemList",cartItemList);
        model.addAttribute("shoppingCart", user.getShoppingCart());

        model.addAttribute("classActiveShipping",true);

        if(missingRequiredField){
            model.addAttribute("missingRequiredField",true);
        }

        return "checkout";
    }


         ///Post checkout form
    @RequestMapping(value = {"/checkout"},method = RequestMethod.POST)
    public String checkoutPost(@RequestParam(value = "id", required = false) Long cartId,@ModelAttribute("shippingAddress") ShippingAddress shippingAddress,
                               @ModelAttribute("billingAddress") BillingAddress billingAddress,
                               @ModelAttribute("payment") Payment payment,
                               @ModelAttribute("billingSameAsShipping") String billingSameAsShipping,
                               @ModelAttribute("shippingMethod") String shippingMethod,
                               Principal principal,Model model){

        ShoppingCart shoppingCart = userService.findByUsername(principal.getName()).getShoppingCart();

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        model.addAttribute("cartItemList",cartItemList);

//        if(cartId != userService.findByUsername(principal.getName()).getShoppingCart().getId()){
//            return "badRequestPage";
//        }

        if(billingSameAsShipping.equals(true)){
            billingAddress.setBillingAddressName(shippingAddress.getShippingAddressName());
            billingAddress.setBillingAddressStreet(shippingAddress.getShippingAddressStreet());
            billingAddress.setBillingAddressCity(shippingAddress.getShippingAddressCity());
            billingAddress.setBillingAddressCountry(shippingAddress.getShippingAddressCountry());
            billingAddress.setBillingAddressZipCode(shippingAddress.getShippingAddressZipCode());
        }

        if(shippingAddress.getShippingAddressStreet().isEmpty() ||
        shippingAddress.getShippingAddressCity().isEmpty() ||
        shippingAddress.getShippingAddressName().isEmpty() ||
        shippingAddress.getShippingAddressCountry().isEmpty() ||
        shippingAddress.getShippingAddressZipCode().isEmpty() ||
        payment.getCardNumber().isEmpty() ||
        payment.getCvc() == 0 ||
        billingAddress.getBillingAddressStreet().isEmpty() ||
                billingAddress.getBillingAddressCity().isEmpty() ||
                billingAddress.getBillingAddressCountry().isEmpty() ||
                billingAddress.getBillingAddressName().isEmpty() ||
                billingAddress.getBillingAddressZipCode().isEmpty())
            return "redirect:checkout?id="+shoppingCart.getId() + "&missingRequiredField=true";

            User user = userService.findByUsername(principal.getName());

            Order order = orderService.createOrder(shoppingCart,shippingAddress,billingAddress,payment,shippingMethod,user);

            mailSender.send(mailConstructor.constructOrderConfirmation(user,order, Locale.ENGLISH));

            shoppingCartService.clearShoppingCart(shoppingCart);

            LocalDate today = LocalDate.now();
            LocalDate estimatedDeliveryDate;

            if(shippingMethod.equals("groundShipping")){
                estimatedDeliveryDate = today.plusDays(5);
            }else{
                estimatedDeliveryDate = today.plusDays(3);
            }

            model.addAttribute("estimatedDeliveryDate",estimatedDeliveryDate);


        return "orderSubmittedPage";

    }


    @RequestMapping(value = {"/setShippingAddress"})
    public String setShippingAddress(@RequestParam("userShippingId") Long userShippingId,
                                     Principal principal, Model model){
        User user = userService.findByUsername(principal.getName());
        UserShipping userShipping = userShippingService.getOne(userShippingId);

        if(userShipping.getUser().getId() != user.getId()){
            return "badRequestPage";
        }else {
            shippingAddressService.setByUserShipping(userShipping,shippingAddress);

            List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

            model.addAttribute("shippingAddress",shippingAddress);
            model.addAttribute("payment",payment);
            model.addAttribute("billingAddress",billingAddress);
            model.addAttribute("cartItemList",cartItemList);
            model.addAttribute("shoppingCart", user.getShoppingCart());

            List<UserShipping> userShippingList = user.getUserShippingList();
            List<UserPayment> userPaymentList = user.getUserPaymentList();

            model.addAttribute("userShippingList",userShippingList);
            model.addAttribute("userPaymentList",userPaymentList);

            model.addAttribute("shippingAddress",shippingAddress);

            model.addAttribute("classActiveShipping",true);

            if(userPaymentList.size()==0){
                model.addAttribute("emptyPaymentList",true);
            }else{
                model.addAttribute("emptyPaymentList",false);
            }

            model.addAttribute("emptyShippingList",false);


            return "checkout";

        }
    }

    @RequestMapping(value = {"/setPaymentMethod"})
    public String setPaymentMethod(@RequestParam("userPaymentId") Long userPaymentId,
                                   Principal principal, Model model){

        User user = userService.findByUsername(principal.getName());
        UserPayment userPayment = userPaymentService.getOne(userPaymentId);
        UserBilling userBilling = userPayment.getUserBilling();

        if(userPayment.getUser().getId() != user.getId()){
            return "badRequestPage";
        }else{
            paymentService.setByUserPayment(userPayment,payment);

            List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

            billingAddressService.setByUserBilling(userBilling,billingAddress);

            model.addAttribute("shippingAddress",shippingAddress);
            model.addAttribute("payment",payment);
            model.addAttribute("billingAddress",billingAddress);
            model.addAttribute("cartItemList",cartItemList);
            model.addAttribute("shoppingCart", user.getShoppingCart());

            List<UserShipping> userShippingList = user.getUserShippingList();
            List<UserPayment> userPaymentList = user.getUserPaymentList();

            model.addAttribute("userShippingList",userShippingList);
            model.addAttribute("userPaymentList",userPaymentList);

            model.addAttribute("shippingAddress",shippingAddress);

            model.addAttribute("classActivePayment",true);


            model.addAttribute("emptyPaymentList",false);

            if(userShippingList.size()==0){
                model.addAttribute("emptyShippingList",true);
            }else{
                model.addAttribute("emptyShippingList",false);
            }

            return "checkout";
        }
    }

}
