package com.fiek.travelGuide.service;

import com.fiek.travelGuide.domain.Location;
import com.fiek.travelGuide.domain.Ticket;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TicketService {

    Ticket save(Ticket ticket);
    List<Ticket> findAll();

    Optional<Ticket> findById(Long id);

    Ticket findOne(Long id);

    Ticket getOne(Long id);

    Ticket findByLocation(Location location);
//    Ticket findByDate(Date date);
}
