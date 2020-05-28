//package com.fiek.adminportal.controller;
//
//import com.fiek.adminportal.domain.Location;
//import com.fiek.adminportal.domain.Ticket;
//import com.fiek.adminportal.service.TicketService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@Controller
//@RequestMapping(value = {"/ticket"})
//public class TicketController {
//
//
//    @Autowired
//    private TicketService ticketService;
//
//    @RequestMapping(value = {"/getAll"})
//    public String getAll(Model model){
//        List<Ticket> ticketList = ticketService.findAll();
//
//        model.addAttribute("ticketList",ticketList);
//
//        return "ticketList";
//    }
//}
