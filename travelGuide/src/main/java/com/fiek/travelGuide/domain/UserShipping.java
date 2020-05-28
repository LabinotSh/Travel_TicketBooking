package com.fiek.travelGuide.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserShipping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userShippingName;
    private String userShippingStreet;
    private String userShippingCity;
    private String userShippingCountry;
    private String userShippingZipCode;
    private boolean userShippingDefault;



    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public UserShipping(){

    }


    public UserShipping(Long id, String userShippingName, String userShippingStreet, String userShippingCity, String userShippingCountry, String userShippingZipCode, boolean userShippingDefault, User user) {
        this.id = id;
        this.userShippingName = userShippingName;
        this.userShippingStreet = userShippingStreet;
        this.userShippingCity = userShippingCity;
        this.userShippingCountry = userShippingCountry;
        this.userShippingZipCode = userShippingZipCode;
        this.userShippingDefault = userShippingDefault;
        this.user = user;
    }

    public boolean isUserShippingDefault() {
        return userShippingDefault;
    }

    public void setUserShippingDefault(boolean userShippingDefault) {
        this.userShippingDefault = userShippingDefault;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserShippingName() {
        return userShippingName;
    }

    public void setUserShippingName(String userShippingName) {
        this.userShippingName = userShippingName;
    }

    public String getUserShippingStreet() {
        return userShippingStreet;
    }

    public void setUserShippingStreet(String userShippingStreet) {
        this.userShippingStreet = userShippingStreet;
    }

    public String getUserShippingCity() {
        return userShippingCity;
    }

    public void setUserShippingCity(String userShippingCity) {
        this.userShippingCity = userShippingCity;
    }

    public String getUserShippingCountry() {
        return userShippingCountry;
    }

    public void setUserShippingCountry(String userShippingCountry) {
        this.userShippingCountry = userShippingCountry;
    }

    public String getUserShippingZipCode() {
        return userShippingZipCode;
    }

    public void setUserShippingZipCode(String userShippingZipCode) {
        this.userShippingZipCode = userShippingZipCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
