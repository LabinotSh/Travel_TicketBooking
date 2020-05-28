package com.fiek.adminportal.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserBilling {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userBillingName;
    private String userBillingStreet;
    private String userBillingCity;
    private String userBillingCountry;
    private String userBillingZipCode;

    @OneToOne(cascade = CascadeType.ALL)
    private UserPayment userPayment;

    public UserBilling(){

    }

    public UserBilling(Long id, String userBillingName, String userBillingStreet, String userBillingCity, String userBillingCountry, String userBillingZipCode, UserPayment userPayment) {
        this.id = id;
        this.userBillingName = userBillingName;
        this.userBillingStreet = userBillingStreet;
        this.userBillingCity = userBillingCity;
        this.userBillingCountry = userBillingCountry;
        this.userBillingZipCode = userBillingZipCode;
        this.userPayment = userPayment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserBillingName() {
        return userBillingName;
    }

    public void setUserBillingName(String userBillingName) {
        this.userBillingName = userBillingName;
    }

    public String getUserBillingStreet() {
        return userBillingStreet;
    }

    public void setUserBillingStreet(String userBillingStreet) {
        this.userBillingStreet = userBillingStreet;
    }

    public String getUserBillingCity() {
        return userBillingCity;
    }

    public void setUserBillingCity(String userBillingCity) {
        this.userBillingCity = userBillingCity;
    }

    public String getUserBillingCountry() {
        return userBillingCountry;
    }

    public void setUserBillingCountry(String userBillingCountry) {
        this.userBillingCountry = userBillingCountry;
    }

    public String getUserBillingZipCode() {
        return userBillingZipCode;
    }

    public void setUserBillingZipCode(String userBillingZipCode) {
        this.userBillingZipCode = userBillingZipCode;
    }

    public UserPayment getUserPayment() {
        return userPayment;
    }

    public void setUserPayment(UserPayment userPayment) {
        this.userPayment = userPayment;
    }
}
