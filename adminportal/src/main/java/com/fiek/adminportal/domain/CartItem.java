package com.fiek.adminportal.domain;


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

//    @OneToOne
//    private Ticket ticket; //// Still ?????

    @OneToMany(mappedBy = "cartItem")
    @JsonIgnore
    private List<LocationToCartItem>  locationToCartItemList;/////Change with Ticket - Location relationship

    @ManyToOne
    @JoinColumn(name="shopping_cart_id")
    private ShoppingCart shoppingCart;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

}
