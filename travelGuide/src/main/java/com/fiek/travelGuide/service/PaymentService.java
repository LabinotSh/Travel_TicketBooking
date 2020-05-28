package com.fiek.travelGuide.service;

import com.fiek.travelGuide.domain.Payment;
import com.fiek.travelGuide.domain.UserPayment;

public interface PaymentService {

    Payment setByUserPayment(UserPayment userPayment,Payment payment);
}
