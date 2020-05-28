package com.fiek.travelGuide.repository;

import com.fiek.travelGuide.domain.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserPaymentRepository extends JpaRepository<UserPayment,Long> {
}
