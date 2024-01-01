package springboot.ticketingtool.service;

import org.springframework.stereotype.Service;
import springboot.ticketingtool.model.TicketModel;
import springboot.ticketingtool.model.TicketUpdationModel;

@Service
public interface TicketService {
    String raiseTicket(TicketModel ticketModel);
    String closeTicket(TicketUpdationModel ticketNo);

    String reopenTicket(TicketUpdationModel ticketNo);
}
