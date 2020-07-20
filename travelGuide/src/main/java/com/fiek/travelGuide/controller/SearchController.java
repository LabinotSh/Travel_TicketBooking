package com.fiek.travelGuide.controller;

import com.fiek.travelGuide.domain.Location;
import com.fiek.travelGuide.domain.User;
import com.fiek.travelGuide.service.LocationService;
import com.fiek.travelGuide.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    @RequestMapping(value = {"/searchByMunicipality"})
    public String searchByMuni(@RequestParam("municipality") String municipality,
                               Model model,
                               Principal principal){

        if(principal != null){
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user",user);
        }
        String classActiveMunicipality = "active"+municipality;
        classActiveMunicipality = classActiveMunicipality.replaceAll("\\s+","");
        classActiveMunicipality = classActiveMunicipality.replaceAll("&","");
        model.addAttribute(classActiveMunicipality,true);

        List<Location> locationList = locationService.findByMunicipality(municipality);

        if(locationList.isEmpty()){
            model.addAttribute("emptyList",true);
            return "destinationList";
        }
        model.addAttribute("locationList",locationList);

        return "destinationList";
    }

    @RequestMapping(value = {"/searchLocation"})
    public  String searchLocation(@ModelAttribute("keyword") String keyword,
                                  Model model, Principal principal){
        if(principal != null){
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user",user);
        }

        List<Location> locationList = locationService.blurrySearch(keyword);

        if(locationList.isEmpty()){
            model.addAttribute("emptyList",true);
            return "destinationList";
        }

        model.addAttribute("locationList",locationList);
        return "destinationList";
    }
}
