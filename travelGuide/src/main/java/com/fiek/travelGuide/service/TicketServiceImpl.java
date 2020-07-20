package com.fiek.travelGuide.service;

import com.fiek.travelGuide.domain.Location;
import com.fiek.travelGuide.domain.Ticket;
import com.fiek.travelGuide.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService{

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> findAll() {
        return (List<Ticket>) ticketRepository.findAll();
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        return ticketRepository.findById(id);
    }

    @Override
    public Ticket findOne(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    @Override
    public Ticket getOne(Long id) {
        return ticketRepository.getOne(id);
    }

    @Override
    public Ticket findByLocation(Location location) {
        return  ticketRepository.findByLocation(location);
    }

//    @Override
//    public Ticket findByDate(Date date) {
//        return ticketRepository.findByDate(date);
//    }


}
