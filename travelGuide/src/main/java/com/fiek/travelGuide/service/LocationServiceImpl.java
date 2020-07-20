package com.fiek.travelGuide.service;

import com.fiek.travelGuide.domain.Location;
import com.fiek.travelGuide.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<Location> locationList =  (List<Location>) locationRepository.findAll();
        List<Location> activeLocations = new ArrayList<>();

        for(Location location : locationList){
            if(location.isActive()){
                activeLocations.add(location);
            }
        }
        return activeLocations;

    }

    @Override
    public Optional<Location> findById(Long id) {
        return locationRepository.findById(id);
    }

    @Override
    public Location getOne(Long id) {
        return locationRepository.getOne(id);
    }

    @Override
    public List<Location> findByMunicipality(String municipality) {
        List<Location> locationList = locationRepository.findByMunicipality(municipality);

        List<Location> activeLocations = new ArrayList<>();

        for(Location location : locationList){
            if(location.isActive()){
                activeLocations.add(location);
            }
        }
        return activeLocations;
    }

    @Override
    public List<Location> blurrySearch(String name){
        List<Location> locationList = locationRepository.findByNameContaining(name);

        List<Location> activeLocations = new ArrayList<>();
        for(Location location : locationList){
            if(location.isActive()){
                activeLocations.add(location);
            }
        }
        return activeLocations;
    }


}
