package springboot.ticketingtool.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserModel {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String branch;
    public UserModel(String userId,String branch){
        this.userId=userId;
        this.branch=branch;
    }
}
