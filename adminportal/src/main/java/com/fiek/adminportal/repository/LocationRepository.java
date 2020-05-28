package com.fiek.adminportal.repository;

import com.fiek.adminportal.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Long> {
}
