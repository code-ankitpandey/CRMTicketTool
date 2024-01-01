package springboot.ticketingtool.model;

import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class TicketModel {
    private String title;
    private String description;
    private String status;
    private String priority;
    private String userId;
    private String service;
    private String Category;
    private String subCategory;
    @Lob
    private byte [] attachment;
}
