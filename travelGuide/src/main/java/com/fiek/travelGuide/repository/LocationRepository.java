package com.fiek.travelGuide.repository;

import com.fiek.travelGuide.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends JpaRepository<Location,Long> {

}
