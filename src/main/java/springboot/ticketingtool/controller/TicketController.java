package springboot.ticketingtool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springboot.ticketingtool.model.TicketModel;
import springboot.ticketingtool.model.TicketUpdationModel;
import springboot.ticketingtool.service.TicketService;

@RestController
public class TicketController {
    @Autowired
    TicketService ticketService;
    @PostMapping("/raiseTicket")
    public String raiseTicket(TicketModel ticketModel){
        return ticketService.raiseTicket(ticketModel);
    }
    @PutMapping("/closeTicket")
    public String closeTicket(@RequestBody TicketUpdationModel ticketUpdationModel){
       return ticketService.closeTicket(ticketUpdationModel);
    }
    @PutMapping("/reopenTicket")
    public String reopenTicket(@RequestBody TicketUpdationModel ticketNo){
        return ticketService.reopenTicket(ticketNo);
    }
//    @PutMapping("/approveTicketL1")
//    public String approveTicketL1(){
//
//    }
//    @PutMapping("/rejectTicketL1")
//    public String rejectTicketL1(){
//
//    }
//    @PutMapping("/rejectTicketL2")
//    public String rejectTicketL2(){
//    }
//    @PutMapping("/needMoreInformation")
//    public String needMoreInformation(){
//
//    }
//    @PutMapping("/approveTicketL2")
//    public String approveTicketL2(){
//
//    }
}
