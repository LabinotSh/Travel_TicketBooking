package com.fiek.travelGuide.repository;

        import com.fiek.travelGuide.domain.CartItem;
        import com.fiek.travelGuide.domain.Location;
        import com.fiek.travelGuide.domain.Order;
        import com.fiek.travelGuide.domain.Ticket;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.repository.CrudRepository;

        import java.util.Date;
        import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

        Ticket findByLocation(Location location);
//        Ticket findByDate(Date date);
}
