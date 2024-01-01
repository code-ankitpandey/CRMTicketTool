package springboot.ticketingtool.model;

import lombok.Data;

@Data
public class ChangeBranchModel {
    private UserModel userModel;
    private BranchModel branchModel;
}
