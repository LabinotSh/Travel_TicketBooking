package com.fiek.travelGuide.controller;

import com.fiek.travelGuide.domain.*;
import com.fiek.travelGuide.domain.security.PasswordResetToken;
import com.fiek.travelGuide.domain.security.Role;
import com.fiek.travelGuide.domain.security.UserRole;
import com.fiek.travelGuide.service.*;
import com.fiek.travelGuide.utility.KSConstants;
import com.fiek.travelGuide.utility.MailConstructor;
import com.fiek.travelGuide.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class HomeController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConstructor mailConstructor;

    @Autowired
    private UserService userService;

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private UserPaymentService userPaymentService;

    @Autowired
    private UserShippingService userShippingService;

    @RequestMapping(value = {"/"})
    public String index(){
        return "index";
    }

//    @RequestMapping(value={"/account"})
//    public String userAccount(){
//        return "userAccount";
//    }

    @RequestMapping(value = {"/admin"})
    public String adminHome(){
        return "admin";
    }

    @RequestMapping(value = {"/login"})
    public String login(Model model){
        model.addAttribute("classActiveLogin", true);
        return "userAccount";
    }

    @RequestMapping(value={"/destinationList"})
    public String destinationList(Model model){
        List<Location> locationList = locationService.findAll();
        model.addAttribute("locationList",locationList);

        return "destinationList";
    }


    @RequestMapping(value = {"/locationDetails"})
    public String locationDetail(@PathParam(value = "id") Long id,
                                 @ModelAttribute("bookingDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date bookingDate,
                                 @ModelAttribute("qty") String qty,
                                 HttpServletRequest request,
                                 Model model,
                                 Principal principal){
        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user",user);
        }

        Location location = locationService.getOne(id);

        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
            location.setBookingDate(simpleDateFormat.parse(String.valueOf(bookingDate)));
            locationService.save(location);
        }catch (ParseException e) {
                e.printStackTrace();
            }

//        location.setBookingDate(bookingDate);
        //Optional<Location> location1 = locationService.findById(id);
        model.addAttribute("location",location);

        List<Integer> qtyList = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        model.addAttribute("qtyList",qtyList);
        model.addAttribute("qty",1);
//        location.setNrOfTickets(location.getNrOfTickets()-Integer.parseInt(qty));

        return "locationDetails";
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
//
    @RequestMapping(value = {"/forgetPassword"})
    public String forgetPassword(HttpServletRequest request,
                                 @ModelAttribute("email") String email,
                                 Model model){

        model.addAttribute("classActiveForgetPassword", true);

        User user = userService.findByEmail(email);

        if(user == null){
            model.addAttribute("emailNotExist",true);
            return  "userAccount";
        }

        String password = SecurityUtility.randomPassword();
        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        userService.save(user);

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();

        SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(appUrl,request.getLocale(),token,user,password);

        mailSender.send(newEmail);

        model.addAttribute("forgetPasswordEmailSent", true);

        return "userAccount";
    }
  ///My profile
    @RequestMapping(value= {"/myProfile"})
    public String myProfile(Model model,
                           Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user",user);
        model.addAttribute("userPaymentList",user.getUserPaymentList());
        model.addAttribute("userShippingList",user.getUserShippingList());
//        model.addAttribute("orderList",user.getOrderList());

        UserShipping userShipping = new UserShipping();
        model.addAttribute("userShipping",userShipping);

        model.addAttribute("listOfCreditCards",true);
        model.addAttribute("listOfShippingAddresses",true);

        List<String> stateList = KSConstants.listOfKSCitiesCodes;
        Collections.sort(stateList);
        model.addAttribute("stateList",stateList);
        model.addAttribute("classActiveEdit",true);

        return "myProfile";
    }

          //List Of Credit Cards
    @RequestMapping(value = {"/listOfCreditCards"})
    public String listCreditCards(Model model,
                                  Principal principal,
                                  HttpServletRequest request){

        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user",user);
        model.addAttribute("userPaymentList",user.getUserPaymentList());
        model.addAttribute("userShippingList",user.getUserShippingList());
//        model.addAttribute("orderList",user.getOrderList());

        model.addAttribute("listOfCreditCards",true);
        model.addAttribute("classActiveBilling",true);
        model.addAttribute("listOfShippingAddresses",true);

        return "myProfile";
    }

        //Add Credit Card
    @RequestMapping(value = {"/addNewCreditCard"})
    public String addNewCreditCard(Model model, Principal principal){

        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user",user);

        model.addAttribute("addNewCreditCard",true);
        model.addAttribute("classActiveBilling",true);

        model.addAttribute("listOfShippingAddresses",true);

        UserBilling userBilling = new UserBilling();
        UserPayment userPayment = new UserPayment();

        model.addAttribute("userBilling",userBilling);
        model.addAttribute("userPayment",userPayment);

        List<String> stateList = KSConstants.listOfKSCitiesCodes;
        Collections.sort(stateList);
        model.addAttribute("stateList",stateList);

        model.addAttribute("userPaymentList",user.getUserPaymentList());
        model.addAttribute("userShippingList",user.getUserShippingList());
//        model.addAttribute("orderList",user.getOrderList());

        return "myProfile";
    }

    //Add Credit card - post
    @RequestMapping(value = {"/addNewCreditCard"}, method = RequestMethod.POST)
    public String addNewCreditCardPost(
            @ModelAttribute("userPayment") UserPayment userPayment,
            @ModelAttribute("userBilling") UserBilling userBilling,
            Principal principal,Model model) {

        User user = userService.findByUsername(principal.getName());
        userService.updateUserBilling(userBilling,userPayment, user);

        model.addAttribute("user",user);
        model.addAttribute("userPaymentList",user.getUserPaymentList());
        model.addAttribute("userShippingList",user.getUserShippingList());
        model.addAttribute("listOfCreditCards",true);
        model.addAttribute("classActiveBilling",true);
        model.addAttribute("listOfShippingAddresses",true);

        return "myProfile";
    }

    //Update Credit Card
    @RequestMapping(value={"/updateCreditCard"})
    public String updateCreditCard(
            @ModelAttribute("id") Long creditCardId,
            Principal principal,
            Model model
    ){
        User user = userService.findByUsername(principal.getName());
        UserPayment userPayment = userPaymentService.getOne(creditCardId);

        if(user.getId() != userPayment.getUser().getId()){
            return "badRequestPage";
        }else{
            model.addAttribute("user",user);
            UserBilling userBilling = userPayment.getUserBilling();
            model.addAttribute("userPayment",userPayment);
            model.addAttribute("userBilling",userBilling);

            model.addAttribute("addNewCreditCard",true);
            model.addAttribute("classActiveBilling",true);
            model.addAttribute("listOfShippingAddresses",true);

            model.addAttribute("userPaymentList",user.getUserPaymentList());
            model.addAttribute("userShippingList",user.getUserShippingList());

            return "myProfile";
        }

    }


    @RequestMapping(value={"/setDefaultPayment"},method = RequestMethod.POST)
    public String setDefaultPayment(
            @ModelAttribute("defaultUserPaymentId") Long defaultPaymentId,
            Principal principal,
            Model model
    ){

        User user = userService.findByUsername(principal.getName());
        userService.setUserDefaultPayment(defaultPaymentId,user);

        model.addAttribute("user",user);
        model.addAttribute("listOfCreditCards",true);
        model.addAttribute("classActiveBilling",true);
        model.addAttribute("listOfShippingAddresses",true);

        model.addAttribute("userPaymentList",user.getUserPaymentList());
        model.addAttribute("userShippingList",user.getUserShippingList());

        return "myProfile";
    }


             ///Remove Credit Card
    @RequestMapping(value={"/removeCreditCard"})
    public String removeCreditCard(
            @ModelAttribute("id") Long creditCardId,
            Principal principal,
            Model model
    ) {
        User user = userService.findByUsername(principal.getName());
        UserPayment userPayment = userPaymentService.getOne(creditCardId);

        if(user.getId() != userPayment.getUser().getId()){
            return "badRequestPage";
        }else{
            model.addAttribute("user",user);

            userPaymentService.removeById(creditCardId);

            model.addAttribute("listOfCreditCards",true);
            model.addAttribute("classActiveBilling",true);
            model.addAttribute("listOfShippingAddresses",true);

            model.addAttribute("userPaymentList",user.getUserPaymentList());
            model.addAttribute("userShippingList",user.getUserShippingList());

            return "myProfile";
        }
    }

    @RequestMapping(value = {"/addNewShippingAddress"})
    public String addNewShippingAddress(Model model, Principal principal){

        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user",user);

        model.addAttribute("addNewShippingAddress",true);
        model.addAttribute("classActiveShipping",true);
        model.addAttribute("listOfCreditCards",true);
//        model.addAttribute("listOfShippingAddresses",true);

       UserShipping userShipping = new UserShipping();

        model.addAttribute("userShipping",userShipping);


        List<String> stateList = KSConstants.listOfKsCitiesNames;
        Collections.sort(stateList);
        model.addAttribute("stateList",stateList);

        model.addAttribute("userPaymentList",user.getUserPaymentList());
        model.addAttribute("userShippingList",user.getUserShippingList());
//        model.addAttribute("orderList",user.getOrderList());

        return "myProfile";
    }

    @RequestMapping(value = {"/addNewShippingAddress"}, method = RequestMethod.POST)
    public String addNewShippingAddressPost(
            @ModelAttribute("userShipping") UserShipping userShipping,
            Principal principal,Model model) {

        User user = userService.findByUsername(principal.getName());
        userService.updateUserShipping(userShipping, user);

        model.addAttribute("user",user);
        model.addAttribute("userPaymentList",user.getUserPaymentList());
        model.addAttribute("userShippingList",user.getUserShippingList());
        model.addAttribute("listOfCreditCards",true);
        model.addAttribute("classActiveShipping",true);
        model.addAttribute("listOfShippingAddresses",true);

        return "myProfile";
    }

    //Shipping addresses listing
    @RequestMapping(value = {"/listOfShippingAddresses"})
    public String listShippingAddresses(Model model,
                                        Principal principal,
                                        HttpServletRequest request){

        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user",user);
        model.addAttribute("userPaymentList",user.getUserPaymentList());
        model.addAttribute("userShippingList",user.getUserShippingList());
//        model.addAttribute("orderList",user.getOrderList());

        model.addAttribute("listOfCreditCards",true);
        model.addAttribute("classActiveShipping",true);
        model.addAttribute("listOfShippingAddresses",true);

        return "myProfile";
    }

    //Update user shipping
    @RequestMapping(value={"/updateUserShipping"})
    public String updateUserShipping(
            @ModelAttribute("id") Long shippingAddressId,
            Principal principal,
            Model model
    ){
        User user = userService.findByUsername(principal.getName());
        UserShipping userShipping = userShippingService.getOne(shippingAddressId);

        if(user.getId() != userShipping.getUser().getId()){
            return "badRequestPage";
        }else{
            model.addAttribute("user",user);
            model.addAttribute("userShipping",userShipping);

            model.addAttribute("addNewShippingAddress",true);
            model.addAttribute("classActiveShipping",true);
            model.addAttribute("listOfCreditCards",true);

            model.addAttribute("userPaymentList",user.getUserPaymentList());
            model.addAttribute("userShippingList",user.getUserShippingList());

            return "myProfile";
        }
    }

    @RequestMapping(value={"/setDefaultShippingAddress"})
    public String setDefaultShippingAddress(
            @ModelAttribute("defaultShippingAddressId") Long defaultShippingId,
            Principal principal,
            Model model
    ){

        User user = userService.findByUsername(principal.getName());
        userService.setUserDefaultShipping(defaultShippingId,user);

        model.addAttribute("user",user);
        model.addAttribute("listOfCreditCards",true);
        model.addAttribute("classActiveShipping",true);
        model.addAttribute("listOfShippingAddresses",true);

        model.addAttribute("userPaymentList",user.getUserPaymentList());
        model.addAttribute("userShippingList",user.getUserShippingList());

        return "myProfile";
    }

    ///Remove Shipping Address
    @RequestMapping(value={"/removeUserShipping"})
    public String removeUserShipping(
            @ModelAttribute("id") Long userShippingId,
            Principal principal,
            Model model
    ) {
        User user = userService.findByUsername(principal.getName());
        UserShipping userShipping = userShippingService.getOne(userShippingId);

        if(user.getId() != userShipping.getUser().getId()){
            return "badRequestPage";
        }else{
            model.addAttribute("user",user);

            userShippingService.removeById(userShippingId);

            model.addAttribute("listOfShippingAddresses",true);
            model.addAttribute("classActiveShipping",true);

            model.addAttribute("userPaymentList",user.getUserPaymentList());
            model.addAttribute("userShippingList",user.getUserShippingList());

            return "myProfile";
        }
    }


    @RequestMapping(value = {"/newAccount"}, method = RequestMethod.POST)
    public String newAccountPost(HttpServletRequest request,
                                 @ModelAttribute("email") String userEmail,
                                 @ModelAttribute("username") String username,
                                 Model model
                                 ) throws Exception{
        model.addAttribute("classActiveNewAccount", true);
        model.addAttribute("email", userEmail);
        model.addAttribute("username",username);

        if(userService.findByUsername(username) != null){
            model.addAttribute("usernameExists",true);
            return "userAccount";
        }
        if(userService.findByEmail(userEmail) != null){
            model.addAttribute("emailExists",true);
            return "userAccount";
        }

        User user = new User();
        user.setUsername((username));
        user.setEmail(userEmail);

        String password = SecurityUtility.randomPassword();
        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        Role role = new Role();
        role.setRoleId(1);
        role.setName("ROLE_USER");
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user,role));
        userService.createUser(user, userRoles);

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();

        SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl,request.getLocale(),token,user,password);

        mailSender.send(email);

        model.addAttribute("emailSent", true);

        return "userAccount";
    }


    @RequestMapping(value = {"/newAccount"})
    public String newAccount(Locale locale,
                             @RequestParam("token") String token,
                             Model model){
        PasswordResetToken passToken = userService.getPasswordResetToken(token);
        if(passToken == null){
            String message = "Invalid Token";
            model.addAttribute("message",message);
            return "redirect:/badRequest";
        }
        User user = passToken.getUser();
        String username = user.getUsername();

        UserDetails userDetails = userSecurityService.loadUserByUsername(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        model.addAttribute("user", user);

        model.addAttribute("classActiveEdit", true);

        return "myProfile";
    }

    @RequestMapping(value = {"/updateUserInfo"}, method = RequestMethod.POST)
    public String updateUserInfo(@ModelAttribute("user") User user,
                                 @ModelAttribute("newPassword") String newPassword,
                                 Model model) throws  Exception{
        User currentUser = userService.getOne(user.getId());

        if(currentUser == null){
            throw new Exception("user not found!");
        }

        /*Check mail already exists*/
        if(userService.findByEmail(user.getEmail())!=null){
            if(userService.findByEmail(user.getEmail()).getId() != currentUser.getId()){
                model.addAttribute("emailExists",true);
                return "myProfile";
            }
        }

        /*Check username already exists*/
        if(userService.findByUsername(user.getUsername())!=null){
            if(userService.findByUsername(user.getUsername()).getId() != currentUser.getId()){
                model.addAttribute("userNameExists",true);
                return "myProfile";
            }
        }

        //update password
        if(newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")){
            BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
            String dbPassword = currentUser.getPassword();
            if(passwordEncoder.matches(user.getPassword(),dbPassword)){
                currentUser.setPassword(passwordEncoder.encode(newPassword));
            }else {
                model.addAttribute("incorrectPassword",true);
                return "myProfile";
            }
        }

        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setUsername(user.getUsername());
        currentUser.setEmail(user.getEmail());

        userService.save(user);

        model.addAttribute("updateSuccess",true);
        model.addAttribute("user",currentUser);
        model.addAttribute("classActiveEdit",true);

        UserDetails userDetails = userSecurityService.loadUserByUsername(currentUser.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,userDetails.getPassword(),
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "myProfile";
    }
}
