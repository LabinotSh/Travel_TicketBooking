package com.fiek.travelGuide.controller;

import com.fiek.travelGuide.domain.*;
import com.fiek.travelGuide.domain.Order;
import com.fiek.travelGuide.domain.Payment;
import com.fiek.travelGuide.domain.ShippingAddress;
import com.fiek.travelGuide.service.*;
import com.fiek.travelGuide.utility.MailConstructor;
import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Controller
public class CheckoutController {

    private ShippingAddress shippingAddress = new ShippingAddress();
    private BillingAddress billingAddress = new BillingAddress();
    private Payment payment = new Payment();

    //Added
    @Autowired
    private PaypalService paypalService;

    public static final String SUCCESS_URL = "/paymentSuccess";
    public static final String CANCEL_URL = "/cancel";

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

        List<Integer> i = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
            model.addAttribute("i",i);


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
    public String checkoutPost(@RequestParam(value = "id", required = false) Long cartId,
                               @ModelAttribute("shippingAddress") ShippingAddress shippingAddress,
                               @ModelAttribute("shippingMethod") String shippingMethod,
                               Principal principal,Model model) throws PayPalRESTException {

        ShoppingCart shoppingCart = userService.findByUsername(principal.getName()).getShoppingCart();
        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        model.addAttribute("cartItemList",cartItemList);
        model.addAttribute("shoppingCart",shoppingCart);

        if(shippingAddress.getShippingAddressStreet().isEmpty() ||
        shippingAddress.getShippingAddressCity().isEmpty() ||
        shippingAddress.getShippingAddressName().isEmpty() ||
        shippingAddress.getShippingAddressCountry().isEmpty() ||
        shippingAddress.getShippingAddressZipCode().isEmpty()
                )
            return "redirect:checkout?id="+shoppingCart.getId() + "&missingRequiredField=true";

            User user = userService.findByUsername(principal.getName());

        com.paypal.api.payments.ShippingAddress shippingAddress1 = new com.paypal.api.payments.ShippingAddress();
        shippingAddress1.setRecipientName(shippingAddress.getShippingAddressName());
        shippingAddress1.setCity(shippingAddress.getShippingAddressCity());
        shippingAddress1.setLine1(shippingAddress.getShippingAddressStreet());
        shippingAddress1.setCountryCode("RS");
        shippingAddress1.setPostalCode("60000");
        //Added
        try {
            //locally http://localhost:8080
            com.paypal.api.payments.Payment payment1 = paypalService.createPayment(shoppingCart.getGrandTotal().doubleValue(),
                    "https://kosovatickets.herokuapp.com" + CANCEL_URL, "https://kosovatickets.herokuapp.com" + SUCCESS_URL);
            for (Links link : payment1.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return "redirect:" + link.getHref();
                }
            }
        }catch (PayPalRESTException e){
            e.printStackTrace();
        }

        LocalDate today = LocalDate.now();
        LocalDate estimatedDeliveryDate;

        if(shippingMethod.equals("groundShipping")){ estimatedDeliveryDate = today.plusDays(5);
        }else{
                estimatedDeliveryDate = today.plusDays(3);
        }

        model.addAttribute("estimatedDeliveryDate",estimatedDeliveryDate);

        return "orderSubmittedPage";
    }

    @RequestMapping(method = RequestMethod.GET, value = CANCEL_URL)
//    @ResponseBody
    public String cancelPay(){
        return "cancel";
    }

    @RequestMapping(method = RequestMethod.GET, value = SUCCESS_URL)
//    @ResponseBody
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,
                             Model model,
                             Principal principal,
                             @ModelAttribute("shippingMethod") String shippingMethod){
        ShoppingCart shoppingCart = userService.findByUsername(principal.getName()).getShoppingCart();

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
        User user = userService.findByUsername(principal.getName());

        model.addAttribute("cartItemList",cartItemList);
        model.addAttribute("shoppingCart",shoppingCart);
        model.addAttribute("user",user);
        try{

            com.paypal.api.payments.Payment payment1 = paypalService.executePayment(paymentId, payerId);
            payment.setHolderName(payerId);
            //payment.setId(Long.parseLong(paymentId));
            @Deprecated
            com.paypal.api.payments.ShippingAddress shippingAddress11 = payment1.getPayer().getPayerInfo().getShippingAddress();
            shippingAddress.setShippingAddressStreet(shippingAddress11.getLine1());
            shippingAddress.setShippingAddressCity(shippingAddress11.getCity());
            shippingAddress.setShippingAddressCountry(shippingAddress11.getCountryCode());
            shippingAddress.setShippingAddressZipCode(shippingAddress11.getPostalCode());
            Order order = orderService.createOrder(shoppingCart,shippingAddress,shippingMethod,user,payment);
            mailSender.send(mailConstructor.constructOrderConfirmation(user,order, Locale.ENGLISH));
            shippingAddress.setOrder(order);
            shippingAddress.setUser(user);
            shoppingCartService.clearShoppingCart(shoppingCart);
            PayerInfo payerInfo = payment1.getPayer().getPayerInfo();
            Transaction transaction = payment1.getTransactions().get(0);
//            ShippingAddress shippingAddress = payment.getPayer().getPayerInfo().getShippingAddress();

            System.out.println("Adresaaa" + shippingAddress11);
            System.out.println(payment1.toJSON());
            System.out.println("payer "+ payerInfo);
            model.addAttribute("payer", payerInfo);
            model.addAttribute("transaction",transaction);
            model.addAttribute("payment",payment1);
            if(payment1.getState().equals("approved")){
                return "orderSubmittedPage";
            }
        }catch(PayPalRESTException e){
            System.out.println(e.getMessage());
        }
        return "redirect:/";
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
