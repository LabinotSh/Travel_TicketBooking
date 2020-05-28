package com.fiek.travelGuide.domain;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class LocationToCartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToOne
    @JoinColumn(name="location_id")
    private Location location;


    @ManyToOne
    @JoinColumn(name="cart_item_id")
    private CartItem cartItem;

    public LocationToCartItem(){

    }

    public LocationToCartItem(Long id, Location location, CartItem cartItem) {
        this.id = id;
        this.location = location;
        this.cartItem = cartItem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }
}
