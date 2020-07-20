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
                                  @ModelAttribute("nrOfTickets") int nrOfTickets,
                                  HttpServletRequest request){

//        }
        location.setActive(true);
        locationService.save(location);

        MultipartFile locationImage = location.getLocationImage();
        MultipartFile locationImage1 = location.getLocationImage1();
        MultipartFile locationImage2 = location.getLocationImage2();
        MultipartFile locationImage3 = location.getLocationImage3();
        MultipartFile locationImage4 = location.getLocationImage4();
        try{
            byte[] bytes = locationImage.getBytes();
            byte[] bytes1 = locationImage1.getBytes();
            byte[] bytes2 = locationImage2.getBytes();
            byte[] bytes3 = locationImage3.getBytes();
            byte[] bytes4 = locationImage4.getBytes();

            String name = location.getId()+".jpg";
            String name1 = 2*(location.getId()) + ".jpg";
            String name2 = 3*(location.getId()) + ".jpg";
            String name3 = 4*(location.getId()) + ".jpg";
            String name4 = 5*(location.getId()) + ".jpg";


                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File("C:\\Users\\TECHCOM\\git\\Projects\\KosovoTravel&Booking\\travelGuide\\src\\main\\resources\\static\\images\\location/" + name)));
                stream.write(bytes);
                stream.close();

            BufferedOutputStream stream1 = new BufferedOutputStream(
                    new FileOutputStream(new File("C:\\Users\\TECHCOM\\git\\Projects\\KosovoTravel&Booking\\travelGuide\\src\\main\\resources\\static\\images\\location/" + name1)));
            stream1.write(bytes1);
            stream1.close();

            BufferedOutputStream stream2 = new BufferedOutputStream(
                    new FileOutputStream(new File("C:\\Users\\TECHCOM\\git\\Projects\\KosovoTravel&Booking\\travelGuide\\src\\main\\resources\\static\\images\\location/" + name2)));
            stream2.write(bytes2);
            stream2.close();

            BufferedOutputStream stream3 = new BufferedOutputStream(
                    new FileOutputStream(new File("C:\\Users\\TECHCOM\\git\\Projects\\KosovoTravel&Booking\\travelGuide\\src\\main\\resources\\static\\images\\location/" + name3)));
            stream3.write(bytes3);
            stream3.close();

            BufferedOutputStream stream4 = new BufferedOutputStream(
                    new FileOutputStream(new File("C:\\Users\\TECHCOM\\git\\Projects\\KosovoTravel&Booking\\travelGuide\\src\\main\\resources\\static\\images\\location/" + name4)));
            stream4.write(bytes4);
            stream4.close();

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
        MultipartFile locationImage1 = location.getLocationImage1();
        MultipartFile locationImage2 = location.getLocationImage2();
        MultipartFile locationImage3 = location.getLocationImage3();
        MultipartFile locationImage4 = location.getLocationImage4();


        if(locationImage != null || locationImage1 != null || locationImage2 != null || locationImage3 != null || locationImage4 != null){
            try {
                byte[] bytes = locationImage.getBytes();
                byte[] bytes1 = locationImage1.getBytes();
                byte[] bytes2 = locationImage2.getBytes();
                byte[] bytes3 = locationImage3.getBytes();
                byte[] bytes4 = locationImage4.getBytes();

                String name = location.getId() + ".jpg";
                String name1 = 2*(location.getId()) + ".jpg";
                String name2 = 3*(location.getId()) + ".jpg";
                String name3 = 4*(location.getId()) + ".jpg";
                String name4 = 5*(location.getId()) + ".jpg";

                Files.delete(Paths.get("C:\\Users\\TECHCOM\\git\\Projects\\KosovoTravel&Booking\\travelGuide\\src\\main\\resources\\static\\images\\location/" + name));
                Files.delete(Paths.get("C:\\Users\\TECHCOM\\git\\Projects\\KosovoTravel&Booking\\travelGuide\\src\\main\\resources\\static\\images\\location/" + name1));
                Files.delete(Paths.get("C:\\Users\\TECHCOM\\git\\Projects\\KosovoTravel&Booking\\travelGuide\\src\\main\\resources\\static\\images\\location/" + name2));
                Files.delete(Paths.get("C:\\Users\\TECHCOM\\git\\Projects\\KosovoTravel&Booking\\travelGuide\\src\\main\\resources\\static\\images\\location/" + name3));
                Files.delete(Paths.get("C:\\Users\\TECHCOM\\git\\Projects\\KosovoTravel&Booking\\travelGuide\\src\\main\\resources\\static\\images\\location/" + name4));

                BufferedOutputStream stream1 = new BufferedOutputStream(
                        new FileOutputStream(new File("C:\\Users\\TECHCOM\\git\\Projects\\KosovoTravel&Booking\\travelGuide\\src\\main\\resources\\static\\images\\location/" + name)));
                stream1.write(bytes);
                stream1.close();

                BufferedOutputStream stream2 = new BufferedOutputStream(
                        new FileOutputStream(new File("C:\\Users\\TECHCOM\\git\\Projects\\KosovoTravel&Booking\\travelGuide\\src\\main\\resources\\static\\images\\location/" + name1)));
                stream2.write(bytes1);
                stream2.close();

                BufferedOutputStream stream3 = new BufferedOutputStream(
                        new FileOutputStream(new File("C:\\Users\\TECHCOM\\git\\Projects\\KosovoTravel&Booking\\travelGuide\\src\\main\\resources\\static\\images\\location/" + name2)));
                stream3.write(bytes2);
                stream3.close();

                BufferedOutputStream stream4 = new BufferedOutputStream(
                        new FileOutputStream(new File("C:\\Users\\TECHCOM\\git\\Projects\\KosovoTravel&Booking\\travelGuide\\src\\main\\resources\\static\\images\\location/" + name3)));
                stream4.write(bytes3);
                stream4.close();

                BufferedOutputStream stream5 = new BufferedOutputStream(
                        new FileOutputStream(new File("C:\\Users\\TECHCOM\\git\\Projects\\KosovoTravel&Booking\\travelGuide\\src\\main\\resources\\static\\images\\location/" + name4)));
                stream5.write(bytes4);
                stream5.close();

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

    @RequestMapping(value={"/remove"},method = RequestMethod.POST)
    public String remove(@ModelAttribute("id") String id, Model model){

        Location location = locationService.getOne(Long.parseLong(id.substring(8)));
       locationService.removeOne(Long.parseLong(id.substring(8)));

        MultipartFile locationImage = location.getLocationImage();
        MultipartFile locationImage1 = location.getLocationImage1();
        MultipartFile locationImage2 = location.getLocationImage2();
        MultipartFile locationImage3 = location.getLocationImage3();
        MultipartFile locationImage4 = location.getLocationImage4();

        try{
//            byte[] bytes = locationImage.getBytes();
//            byte[] bytes1 = locationImage1.getBytes();
//            byte[] bytes2 = locationImage2.getBytes();
//            byte[] bytes3 = locationImage3.getBytes();
//            byte[] bytes4 = locationImage4.getBytes();

            String name = location.getId() + ".jpg";
            String name1 = 2*(location.getId()) + ".jpg";
            String name2 = 3*(location.getId()) + ".jpg";
            String name3 = 4*(location.getId()) + ".jpg";
            String name4 = 5*(location.getId()) + ".jpg";

            Files.delete(Paths.get("C:\\Users\\TECHCOM\\git\\Projects\\KosovoTravel&Booking\\travelGuide\\src\\main\\resources\\static\\images\\location/" + name));
            Files.delete(Paths.get("C:\\Users\\TECHCOM\\git\\Projects\\KosovoTravel&Booking\\travelGuide\\src\\main\\resources\\static\\images\\location/" + name1));
            Files.delete(Paths.get("C:\\Users\\TECHCOM\\git\\Projects\\KosovoTravel&Booking\\travelGuide\\src\\main\\resources\\static\\images\\location/" + name2));
            Files.delete(Paths.get("C:\\Users\\TECHCOM\\git\\Projects\\KosovoTravel&Booking\\travelGuide\\src\\main\\resources\\static\\images\\location/" + name3));
            Files.delete(Paths.get("C:\\Users\\TECHCOM\\git\\Projects\\KosovoTravel&Booking\\travelGuide\\src\\main\\resources\\static\\images\\location/" + name4));

        }catch (IOException e) {
            e.printStackTrace();
        }

        List<Location> locationList = locationService.findAll();
        model.addAttribute("locationList",locationList);

        return "redirect:/location/locationList";

    }


}
