package springboot.ticketingtool.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Rectifier {
    @Id
    @Column(name = "rectifier_id")
    private String rectifierId;
    private String service;
    private String category;
}
