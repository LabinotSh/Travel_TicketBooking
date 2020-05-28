package com.fiek.travelGuide.service;

import com.fiek.travelGuide.domain.UserShipping;
import com.fiek.travelGuide.repository.UserShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserShippingServiceImpl implements UserShippingService {

    @Autowired
    private UserShippingRepository userShippingRepository;

    @Override
    public UserShipping getOne(Long id) {
        return userShippingRepository.getOne(id);
    }

    @Override
    public void removeById(Long id) {
        userShippingRepository.deleteById(id);
    }
}
