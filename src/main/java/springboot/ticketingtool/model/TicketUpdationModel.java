package springboot.ticketingtool.model;

import lombok.Data;

@Data
public class TicketUpdationModel {
    private Long ticketNo;
    private String user;
    private String comment;
}
