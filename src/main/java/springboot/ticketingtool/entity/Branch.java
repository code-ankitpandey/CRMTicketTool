package springboot.ticketingtool.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity
public class Branch {
    @Id
    private String branchId;
    private String city;
    private String state;
    private String approver1;
    private String approver2;
    private String approver3;
    private boolean isActive=false;
    private LocalDateTime creationDate=LocalDateTime.now();

}
