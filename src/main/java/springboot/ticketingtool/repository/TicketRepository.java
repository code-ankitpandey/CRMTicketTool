package springboot.ticketingtool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.ticketingtool.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
}
