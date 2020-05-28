package com.fiek.travelGuide.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
public class BillingAddress {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String billingAddressName;
    private String billingAddressStreet;
    private String billingAddressCity;
    private String billingAddressCountry;
    private String billingAddressZipCode;

    @OneToOne
    @EqualsAndHashCode.Exclude
    private Order order;

    public BillingAddress(){

    }

    public BillingAddress(Long id, String billingAddressName, String billingAddressStreet, String billingAddressCity, String billingAddressCountry, String billingAddressZipCode, Order order) {
        this.id = id;
        this.billingAddressName = billingAddressName;
        this.billingAddressStreet = billingAddressStreet;
        this.billingAddressCity = billingAddressCity;
        this.billingAddressCountry = billingAddressCountry;
        this.billingAddressZipCode = billingAddressZipCode;
        this.order = order;
    }

    public String getBillingAddressName() {
        return billingAddressName;
    }

    public void setBillingAddressName(String billingAddressName) {
        this.billingAddressName = billingAddressName;
    }

    public String getBillingAddressStreet() {
        return billingAddressStreet;
    }

    public void setBillingAddressStreet(String billingAddressStreet) {
        this.billingAddressStreet = billingAddressStreet;
    }

    public String getBillingAddressCity() {
        return billingAddressCity;
    }

    public void setBillingAddressCity(String billingAddressCity) {
        this.billingAddressCity = billingAddressCity;
    }

    public String getBillingAddressCountry() {
        return billingAddressCountry;
    }

    public void setBillingAddressCountry(String billingAddressCountry) {
        this.billingAddressCountry = billingAddressCountry;
    }

    public String getBillingAddressZipCode() {
        return billingAddressZipCode;
    }

    public void setBillingAddressZipCode(String billingAddressZipCode) {
        this.billingAddressZipCode = billingAddressZipCode;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
