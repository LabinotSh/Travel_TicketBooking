//package com.fiek.adminportal.domain;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.Data;
//
//import javax.persistence.*;
//import java.util.Date;
//import java.util.List;
//
//@Data
//@Entity
//public class Ticket {
//
//
//    ///// Added later ////>>>>????
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//    private Date bookingDate;
//    private boolean isCancelled;
//    private int inStockNumber;
//
//
//    //??????
//    private Long bookingPrice;
//
//    public int getInStockNumber() {
//        return inStockNumber;
//    }
//
//    public void setInStockNumber(int inStockNumber) {
//        this.inStockNumber = inStockNumber;
//    }
//
//
//    public Long getBookingPrice() {
//        return bookingPrice;
//    }
//
//    public void setBookingPrice(Long bookingPrice) {
//        this.bookingPrice = bookingPrice;
//    }
//
//    @ManyToOne
//    @JoinColumn(name="user_id")
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name="location_id")
//    private Location location;
//
//    ///Change it to location if it does not work
////    @OneToMany(mappedBy = "ticket")
////    @JsonIgnore
////    private List<TicketToCartItem> ticketToCartItemList;
//
//    public Ticket(){
//
//    }
//    public Ticket(int inStockNumber){
//        this.inStockNumber = inStockNumber;
//    }
//
//
//    public Ticket(Long id, Date bookingDate, boolean isCancelled, int inStockNumber, Long bookingPrice, User user, Location location) {
//        this.id = id;
//        this.bookingDate = bookingDate;
//        this.isCancelled = isCancelled;
//        this.inStockNumber = inStockNumber;
//        this.bookingPrice = bookingPrice;
//        this.user = user;
//        this.location = location;
////        this.ticketToCartItemList = ticketToCartItemList;
//    }
////
////    public List<TicketToCartItem> getTicketToCartItemList() {
////        return ticketToCartItemList;
////    }
////
////    public void setTicketToCartItemList(List<TicketToCartItem> ticketToCartItemList) {
////        this.ticketToCartItemList = ticketToCartItemList;
////    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Date getBookingDate() {
//        return bookingDate;
//    }
//
//    public void setBookingDate(Date bookingDate) {
//        this.bookingDate = bookingDate;
//    }
//
//    public boolean isCancelled() {
//        return isCancelled;
//    }
//
//    public void setCancelled(boolean cancelled) {
//        isCancelled = cancelled;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public Location getLocation() {
//        return location;
//    }
//
//    public void setLocation(Location location) {
//        this.location = location;
//    }
//}
