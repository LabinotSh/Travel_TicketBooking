package com.fiek.travelGuide.service;

import com.fiek.travelGuide.domain.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {

    Location save(Location location);
    List<Location> findAll();
    Optional<Location> findById(Long id);

    Location getOne(Long id);
}
