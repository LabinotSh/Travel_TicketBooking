package com.fiek.travelGuide.service;

import com.fiek.travelGuide.domain.UserPayment;
import com.fiek.travelGuide.repository.UserPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPaymentServiceImpl implements UserPaymentService {

    @Autowired
    private UserPaymentRepository userPaymentRepository;

    @Override
    public Optional<UserPayment> findById(Long id) {
        return Optional.of(userPaymentRepository.findById(id).orElse(new UserPayment()));
    }

    @Override
    public UserPayment getOne(Long id) {
        return userPaymentRepository.getOne(id);
    }

    @Override
    public void removeById(Long id) {
        userPaymentRepository.deleteById(id);
    }
}
