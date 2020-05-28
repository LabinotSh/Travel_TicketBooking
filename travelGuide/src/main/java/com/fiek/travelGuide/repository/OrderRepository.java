package com.fiek.travelGuide.repository;

import com.fiek.travelGuide.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
