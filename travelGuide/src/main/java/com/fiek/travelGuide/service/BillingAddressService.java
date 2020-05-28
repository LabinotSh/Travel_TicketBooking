package com.fiek.travelGuide.service;

import com.fiek.travelGuide.domain.BillingAddress;
import com.fiek.travelGuide.domain.UserBilling;

public interface BillingAddressService {

    BillingAddress setByUserBilling(UserBilling userBilling,BillingAddress billingAddress);
}
