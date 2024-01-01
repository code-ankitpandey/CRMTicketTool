package springboot.ticketingtool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springboot.ticketingtool.entity.Ticket;
import springboot.ticketingtool.model.TicketModel;
import springboot.ticketingtool.model.TicketUpdationModel;
import springboot.ticketingtool.repository.BranchRepository;
import springboot.ticketingtool.repository.RectifierRepository;
import springboot.ticketingtool.repository.TicketRepository;
import springboot.ticketingtool.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class TicketServiceImpl implements TicketService{

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RectifierRepository rectifierRepository;

    static Random random=new Random();

    @Override
    public String raiseTicket(TicketModel ticketModel) {
        Ticket ticket=new Ticket();
        ticket.setService(ticketModel.getService());
        ticket.setCategory(ticketModel.getCategory());
        ticket.setTitle(ticketModel.getTitle());
        ticket.setDescription(ticketModel.getDescription());
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUserId(ticketModel.getUserId());
        ticket.setUpdateAt(LocalDateTime.now());
        ticket.setPriority(ticketModel.getPriority());
        ticket.setRectiferId(getRectifierID(ticket.getService(),ticket.getCategory()));
        ticket.setAttachment(ticketModel.getAttachment());
        ticket.getComments().add("Ticket raised successfully at "+LocalDateTime.now());
        ticket.setUpdateAt(LocalDateTime.now());
        ticket.setStatus("WAITING FOR L1 APPROVAL");
        ticketRepository.save(ticket);
        return "Ticket raised successfully";
    }

    @Override
    public String closeTicket(TicketUpdationModel ticketNo) {
        Optional<Ticket>optionalTicket=ticketRepository.findById(ticketNo.getTicketNo());
        if (optionalTicket.isEmpty()){
            return "Invalid Ticket";
        }
        Ticket ticket=optionalTicket.get();
        if (!ticket.getUserId().equalsIgnoreCase(ticketNo.getUser())){
            return "Ticket not matching with the raiser";
        }
        if (ticket.getIsClosed()){
            return "Ticket already closed";
        }
        String currStatus=ticket.getStatus();
        ticket.getComments().add(ticketNo.getUser()+" changed the status form "+currStatus+" to "+" CLOSED.");
        ticket.setStatus("CLOSED");
        ticket.setIsClosed(true);
        ticketRepository.save(ticket);
        return "Ticket Closed successfully";
    }

    @Override
    public String reopenTicket(TicketUpdationModel ticketUpdationModel) {
        Optional<Ticket>ticketOptional=ticketRepository.findById(ticketUpdationModel.getTicketNo());
        if (ticketOptional.isEmpty()){
            return "Invalid Ticket";
        }
        Ticket ticket=ticketOptional.get();
        if(ticket.getIsClosed()){
            return "Ticket already closed";
        }
        if(ticket.getStatus().equalsIgnoreCase("RESOLVED")){
            String currentStatus=ticket.getStatus();
            ticket.setStatus("REOPEN");
            ticket.getComments().add(ticketUpdationModel.getUser()+" changed the status from "+currentStatus);
            ticket.setIsClosed(false);
            ticketRepository.save(ticket);
        }
        return "Ticket can't be REOPENED.";

    }

    public String getRectifierID(String service,String category){
        List<String>list=rectifierRepository.findAllByServiceAndCategory(service,category);
        return list.get(random.nextInt(0,list.size()));
    }

}
