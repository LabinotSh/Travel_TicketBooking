package com.fiek.adminportal.service;

import com.fiek.adminportal.domain.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {

    Location save(Location location);

    List<Location> findAll();

    Location getOne(Long id);

    Optional<Location> findById(Long id);

    void removeOne(Long id);

}
