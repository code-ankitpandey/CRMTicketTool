package springboot.ticketingtool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springboot.ticketingtool.model.BranchModel;
import springboot.ticketingtool.model.ChangeBranchModel;
import springboot.ticketingtool.model.RectifierModel;
import springboot.ticketingtool.model.UserModel;
import springboot.ticketingtool.service.AdminService;

@RestController
public class    AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/addUser")
    public String addUser(@RequestBody UserModel userModel){
        return adminService.addUser(userModel,"USER");
    }

    @PostMapping("/addNewAdmin")
    public String addAdmin(@RequestBody UserModel userModel){
        return adminService.addUser(userModel,"ADMIN");
    }

    @PostMapping("/addBranch")
    public String addBranch(@RequestBody BranchModel branchModel){
        return adminService.addBranch(branchModel);
    }

    @PutMapping("/makeApprover")
    public String addApprover(@RequestBody UserModel userModel){
        return adminService.makeApprover(userModel);
    }

    @PutMapping("/changeBranch")
    public String changeBranch(@RequestBody ChangeBranchModel changeBranchModel){
        return adminService.changeBranch(changeBranchModel.getUserModel(),changeBranchModel.getBranchModel());
    }
    @DeleteMapping("/deleteUser")
    public String disableUser(@RequestBody UserModel userModel){
        return adminService.disableUser(userModel);
    }

    @DeleteMapping("/deleteBranch")
    public String disableBranch(@RequestBody BranchModel branchModel){
        return adminService.disableBranch(branchModel);
    }
    @PutMapping("/makeL2Approver")
    public String makeL2Approver(@RequestBody UserModel userModel){
        return adminService.makeL2Approver(userModel);
    }

    @PutMapping ("/removeApprovalRightsL1")
    public String removeApprovalRightsL1(@RequestBody UserModel userModel){
        return adminService.removeApprovalRightsL1(userModel);
    }
    @PutMapping ("/removeApprovalRightsL2")
    public String removeApprovalRights(@RequestBody UserModel userModel){
        return adminService.removeApprovalRightsL2(userModel);
    }

    @PostMapping("/makeRectifier")
    public String makeRectifier(@RequestBody RectifierModel rectifierModel){
        return adminService.makeRectifier(rectifierModel);
    }

}
