package com.fiek.travelGuide.service;

import com.fiek.travelGuide.domain.Location;
import com.fiek.travelGuide.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService{

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public Location save(Location location) {
        return locationRepository.save(location);
    }

    public List<Location> findAll(){
        return (List<Location>) locationRepository.findAll();
    }

    @Override
    public Optional<Location> findById(Long id) {
        return locationRepository.findById(id);
    }

    @Override
    public Location getOne(Long id) {
        return locationRepository.getOne(id);
    }



}
