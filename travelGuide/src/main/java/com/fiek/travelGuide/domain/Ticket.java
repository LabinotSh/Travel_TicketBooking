package com.fiek.travelGuide.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Ticket {


    ///// Added later ////>>>>????
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private Date bookingDate;
    private boolean isCancelled;
    private String nrTicketsCreated;

    /////
    private Long bookingPrice;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
//
    @OneToOne(cascade = CascadeType.ALL)
    private Location location;

    ///Change it to location if it does not work
    @OneToMany(mappedBy = "ticket")
    @JsonIgnore
    private List<LocationToCartItem> ticketToCartItemList;

//    public int getInStockNumber() {
//        return inStockNumber;
//    }
//
//    public void setInStockNumber(int inStockNumber) {
//        this.inStockNumber = inStockNumber;
//    }

    public Long getBookingPrice() {
        return bookingPrice;
    }

    public void setBookingPrice(Long bookingPrice) {
        this.bookingPrice = bookingPrice;
    }

    public Ticket(){

    }

    public  Ticket(String nrTicketsCreated){
        this.nrTicketsCreated = nrTicketsCreated;
    }

    public Ticket(Long id, Date bookingDate, boolean isCancelled, Long bookingPrice, User user, Location location, List<LocationToCartItem> ticketToCartItemList) {
        this.id = id;
        this.bookingDate = bookingDate;
        this.isCancelled = isCancelled;
        this.bookingPrice = bookingPrice;
        this.user = user;
        this.location = location;
        this.ticketToCartItemList = ticketToCartItemList;
    }

    public String getNrTicketsCreated() {
        return nrTicketsCreated;
    }

    public void setNrTicketsCreated(String nrTicketsCreated) {
        this.nrTicketsCreated = nrTicketsCreated;
    }

    public List<LocationToCartItem> getTicketToCartItemList() {
        return ticketToCartItemList;
    }

    public void setTicketToCartItemList(List<LocationToCartItem> ticketToCartItemList) {
        this.ticketToCartItemList = ticketToCartItemList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
