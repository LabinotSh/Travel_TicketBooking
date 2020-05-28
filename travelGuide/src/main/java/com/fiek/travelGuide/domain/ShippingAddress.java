package com.fiek.travelGuide.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String shippingAddressName;
    private String shippingAddressStreet;
    private String shippingAddressCity;
    private String shippingAddressCountry;
    private String shippingAddressZipCode;



    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToOne
    @EqualsAndHashCode.Exclude
    private Order order;

    public ShippingAddress(){

    }

    public ShippingAddress(Long id, String shippingAddressName, String shippingAddressStreet, String shippingAddressCity, String shippingAddressCountry, String shippingAddressZipCode, User user) {
        this.id = id;
        this.shippingAddressName = shippingAddressName;
        this.shippingAddressStreet = shippingAddressStreet;
        this.shippingAddressCity = shippingAddressCity;
        this.shippingAddressCountry = shippingAddressCountry;
        this.shippingAddressZipCode = shippingAddressZipCode;
        this.user = user;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getShippingAddressName() {
        return shippingAddressName;
    }

    public void setShippingAddressName(String shippingAddressName) {
        this.shippingAddressName = shippingAddressName;
    }

    public String getShippingAddressStreet() {
        return shippingAddressStreet;
    }

    public void setShippingAddressStreet(String shippingAddressStreet) {
        this.shippingAddressStreet = shippingAddressStreet;
    }

    public String getShippingAddressCity() {
        return shippingAddressCity;
    }

    public void setShippingAddressCity(String shippingAddressCity) {
        this.shippingAddressCity = shippingAddressCity;
    }

    public String getShippingAddressCountry() {
        return shippingAddressCountry;
    }

    public void setShippingAddressCountry(String shippingAddressCountry) {
        this.shippingAddressCountry = shippingAddressCountry;
    }

    public String getShippingAddressZipCode() {
        return shippingAddressZipCode;
    }

    public void setShippingAddressZipCode(String shippingAddressZipCode) {
        this.shippingAddressZipCode = shippingAddressZipCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
