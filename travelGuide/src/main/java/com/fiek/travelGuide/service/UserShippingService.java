package com.fiek.travelGuide.service;

import com.fiek.travelGuide.domain.UserShipping;

public interface UserShippingService {

    UserShipping getOne(Long id);

    void removeById(Long id);
}
