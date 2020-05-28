package com.fiek.travelGuide.service;


import com.fiek.travelGuide.domain.UserPayment;

import java.util.Optional;

public interface UserPaymentService {
    Optional<UserPayment> findById(Long id);
    UserPayment getOne(Long id);
    void removeById(Long id);
}
