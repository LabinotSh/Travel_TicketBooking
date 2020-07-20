package com.fiek.travelGuide.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int qty;
    private BigDecimal subTotal;

    @OneToOne
    private Location location; ///Change it to ticket with location relation

    @OneToOne
    private Ticket ticket; //// Still ?????

    @OneToMany(mappedBy = "cartItem")
    @JsonIgnore
    private List<LocationToCartItem>  locationToCartItemList;/////Change with Ticket - Location relationship


    @ManyToOne
    @JoinColumn(name="shopping_cart_id")
    private ShoppingCart shoppingCart;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;


    public CartItem() {
    }

    public CartItem(Long id, int qty, BigDecimal subTotal, Location location, List<LocationToCartItem> locationToCartItemList, ShoppingCart shoppingCart, Order order, Ticket ticket) {
        this.id = id;
        this.qty = qty;
        this.subTotal = subTotal;
        this.location = location;
        this.locationToCartItemList = locationToCartItemList;
        this.shoppingCart = shoppingCart;
        this.order = order;
        this.ticket =ticket;
    }

    //Added
    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<LocationToCartItem> getLocationToCartItemList() {
        return locationToCartItemList;
    }

    public void setLocationToCartItemList(List<LocationToCartItem> locationToCartItemList) {
        this.locationToCartItemList = locationToCartItemList;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
