package springboot.ticketingtool.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Ticket {
    @Id
    @Column(name = "ticket_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;
    private String title;
    private String description;
    private String status;
    private String priority;
    private LocalDateTime createdAt=LocalDateTime.now();
    private LocalDateTime updateAt;
    @Column(name = "user_id")
    private String userId;
    private String rectiferId;
    private String service;
    private String Category;
    private String subCategory;
    @ElementCollection
    private List<String>comments;
    private Boolean isClosed=false;
    @Lob
    byte[] attachment;

}
