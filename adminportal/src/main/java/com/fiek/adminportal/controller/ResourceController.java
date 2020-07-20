package com.fiek.adminportal.controller;

import com.fiek.adminportal.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ResourceController {

    @Autowired
    private LocationService locationService;

    @RequestMapping(value = {"/location/removeList"},method = RequestMethod.POST)
    public String removeList(@RequestBody ArrayList<String> locationIdList, Model model){

        for(String id : locationIdList){
            String locationId = id.substring(8);
            locationService.removeOne(Long.parseLong(locationId));
        }

        return "delete success";
    }
}
