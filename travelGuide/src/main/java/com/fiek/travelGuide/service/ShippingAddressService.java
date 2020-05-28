package com.fiek.travelGuide.service;

import com.fiek.travelGuide.domain.ShippingAddress;
import com.fiek.travelGuide.domain.UserShipping;

public interface ShippingAddressService  {

    ShippingAddress setByUserShipping(UserShipping userShipping,ShippingAddress shippingAddress);
}
