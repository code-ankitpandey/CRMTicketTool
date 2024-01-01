package springboot.ticketingtool.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ticketing_User")
public class User {
    @Id
    @Column(name = "user_id")
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String password;
    private String branch;
    private boolean isEnabled=false;
    private LocalDateTime creationDate=LocalDateTime.now();
}
