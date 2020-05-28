package com.fiek.adminportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String address;// ????
    private Long bookingPrice;
    private String municipality;
    private int nrOfTickets;

    //Added
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private Date bookingDate;

    @Column(columnDefinition = "text")
    private String description;

    /// Added later -- -??? still ??
//    @OneToMany(cascade = CascadeType.ALL,mappedBy = "location")
//    private Set<Ticket> ticketSet;

    @OneToMany(mappedBy = "location")
    @JsonIgnore
    private List<LocationToCartItem> locationToCartItemList;

    @Transient
    private MultipartFile locationImage;

    public Location(){
    }



    public Location(Long id, String name, String address, Long bookingPrice, String municipality, String description, int nrOfTickets, List<LocationToCartItem> locationToCartItemList, MultipartFile locationImage) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.bookingPrice = bookingPrice;
        this.municipality = municipality;
        this.description = description;
        this.nrOfTickets = nrOfTickets;
        this.locationToCartItemList = locationToCartItemList;
        this.locationImage = locationImage;
    }

    public List<LocationToCartItem> getLocationToCartItemList() {
        return locationToCartItemList;
    }

    public void setLocationToCartItemList(List<LocationToCartItem> locationToCartItemList) {
        this.locationToCartItemList = locationToCartItemList;
    }
    //    public Set<Ticket> getTicketSet() {
//        return ticketSet;
//    }
//
//    public void setTicketSet(Set<Ticket> ticketSet) {
//        this.ticketSet = ticketSet;
//    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getBookingPrice() {
        return bookingPrice;
    }

    public void setBookingPrice(Long bookingPrice) {
        this.bookingPrice = bookingPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNrOfTickets() {
        return nrOfTickets;
    }

    public void setNrOfTickets(int nrOfTickets) {
        this.nrOfTickets = nrOfTickets;
    }

    public MultipartFile getLocationImage() {
        return locationImage;
    }

    public void setLocationImage(MultipartFile locationImage) {
        this.locationImage = locationImage;
    }
}
