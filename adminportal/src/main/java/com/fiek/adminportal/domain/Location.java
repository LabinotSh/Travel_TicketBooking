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
    private boolean active=true;
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

    @Transient
    private MultipartFile locationImage1;

    @Transient
    private MultipartFile locationImage2;

    @Transient
    private MultipartFile locationImage3;
    @Transient
    private MultipartFile locationImage4;

    public Location(){
    }

    public Location(Long id, String name, String address, Long bookingPrice, String municipality, int nrOfTickets, boolean active, Date bookingDate, String description, List<LocationToCartItem> locationToCartItemList, MultipartFile locationImage, MultipartFile locationImage1, MultipartFile locationImage2, MultipartFile locationImage3, MultipartFile locationImage4) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.bookingPrice = bookingPrice;
        this.municipality = municipality;
        this.nrOfTickets = nrOfTickets;
        this.active = active;
        this.bookingDate = bookingDate;
        this.description = description;
        this.locationToCartItemList = locationToCartItemList;
        this.locationImage = locationImage;
        this.locationImage1 = locationImage1;
        this.locationImage2 = locationImage2;
        this.locationImage3 = locationImage3;
        this.locationImage4 = locationImage4;
    }

    public MultipartFile getLocationImage4() {
        return locationImage4;
    }

    public void setLocationImage4(MultipartFile locationImage4) {
        this.locationImage4 = locationImage4;
    }

    public MultipartFile getLocationImage1() {
        return locationImage1;
    }

    public void setLocationImage1(MultipartFile locationImage1) {
        this.locationImage1 = locationImage1;
    }

    public MultipartFile getLocationImage2() {
        return locationImage2;
    }

    public void setLocationImage2(MultipartFile locationImage2) {
        this.locationImage2 = locationImage2;
    }

    public MultipartFile getLocationImage3() {
        return locationImage3;
    }

    public void setLocationImage3(MultipartFile locationImage3) {
        this.locationImage3 = locationImage3;
    }

    public Location(Long id, String name, String address, Long bookingPrice, String municipality, int nrOfTickets, boolean active, Date bookingDate, String description, List<LocationToCartItem> locationToCartItemList, MultipartFile locationImage) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.bookingPrice = bookingPrice;
        this.municipality = municipality;
        this.nrOfTickets = nrOfTickets;
        this.active = active;
        this.bookingDate = bookingDate;
        this.description = description;
        this.locationToCartItemList = locationToCartItemList;
        this.locationImage = locationImage;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
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
