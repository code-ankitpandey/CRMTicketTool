package springboot.ticketingtool.model;

import lombok.Data;
import org.springframework.web.bind.annotation.ModelAttribute;

@Data
public class RectifierModel {
    private String rectifierId;
    private String service;
    private String category;
}
