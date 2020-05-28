package com.fiek.adminportal.controller;

import com.fiek.adminportal.domain.Location;
import com.fiek.adminportal.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @RequestMapping(value = {"/add"}, method = RequestMethod.GET)
    public String addLocation(Model model){
        Location location = new Location();
        model.addAttribute("location",location);
        return "addLocation";
    }

    @RequestMapping(value = {"/add"}, method = RequestMethod.POST)
    public String addLocationPost(@ModelAttribute("location") Location location,
                                  HttpServletRequest request){

        locationService.save(location);

        MultipartFile locationImage = location.getLocationImage();
        try{
            byte[] bytes = locationImage.getBytes();
            String name = location.getId()+".jpg";


                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File("C:\\Users\\TECHCOM\\Downloads\\travelGuide\\src\\main\\resources\\static\\images\\location/" + name)));
                stream.write(bytes);
                stream.close();

//                BufferedOutputStream stream1 = new BufferedOutputStream(
//                        new FileOutputStream(new File("C:\\Users\\TECHCOM\\Downloads\\adminportal\\src\\main\\resources\\static\\images\\location/" + name)));
//                stream1.write(bytes);
//                stream1.close();



        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:locationList";
    }

    @RequestMapping(value = {"/locationInfo"})
    public String locationInfo(@RequestParam(value = "id") Long id,Model model){

        Location location = locationService.getOne(id);
        Optional<Location> location1 = locationService.findById(id);

        model.addAttribute("location",location);

        return "locationInfo";
    }

    @RequestMapping(value = {"/updateLocation"})
    public String updateLocation(@RequestParam(value = "id")Long id, Model model){
        Location location = locationService.getOne(id);
        model.addAttribute("location",location);

        return "updateLocation";
    }

    @RequestMapping(value = {"/updateLocation"}, method = RequestMethod.POST)
    public String updateLocationPost(@ModelAttribute("location") Location location,
                                     HttpServletRequest request
                                     ){
        locationService.save(location);
        MultipartFile locationImage = location.getLocationImage();
        if(locationImage != null){
            try {
                byte[] bytes = locationImage.getBytes();
                String name = location.getId() + ".jpg";

                Files.delete(Paths.get("C:/Users/TECHCOM/Downloads/travelGuide/src/main/resources/static/images/location/" + name));

                BufferedOutputStream stream1 = new BufferedOutputStream(
                        new FileOutputStream(new File("C:\\Users\\TECHCOM\\Downloads\\travelGuide\\src\\main\\resources\\static\\images\\location/" + name)));
                stream1.write(bytes);
                stream1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return "redirect:/location/locationInfo?id="+location.getId();
    }

    @RequestMapping(value = {"/locationList"})
    public String locationList(Model model){
        List<Location> locationList = locationService.findAll();

        model.addAttribute("locationList",locationList);

        return "locationList";
    }


}
