package springboot.ticketingtool.service;

import org.springframework.stereotype.Service;
import springboot.ticketingtool.model.BranchModel;
import springboot.ticketingtool.model.RectifierModel;
import springboot.ticketingtool.model.UserModel;

@Service
public interface AdminService {
    String addUser(UserModel userModel,String role);

    String addBranch(BranchModel branchModel);

    String makeApprover(UserModel userModel);

    String changeBranch(UserModel userModel, BranchModel branchModel);

    String disableUser(UserModel userModel);

    String disableBranch(BranchModel branchModel);

    String makeL2Approver(UserModel userModel);

    String removeApprovalRightsL1(UserModel userModel);

    String removeApprovalRightsL2(UserModel userModel);

    String makeRectifier(RectifierModel rectifierModel);


}
