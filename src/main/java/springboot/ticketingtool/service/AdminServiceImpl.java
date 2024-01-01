package springboot.ticketingtool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springboot.ticketingtool.entity.Branch;
import springboot.ticketingtool.entity.Rectifier;
import springboot.ticketingtool.entity.User;
import springboot.ticketingtool.model.BranchModel;
import springboot.ticketingtool.model.RectifierModel;
import springboot.ticketingtool.model.UserModel;
import springboot.ticketingtool.repository.BranchRepository;
import springboot.ticketingtool.repository.RectifierRepository;
import springboot.ticketingtool.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BranchRepository branchRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RectifierRepository rectifierRepository;

    public String addUser(UserModel userModel, String role) {
        try {
            if (userRepository.existsById(userModel.getUserId())) {
                return "UserID Already Present";
            } else if (!branchRepository.existsById(userModel.getBranch())) {
                return "Branch Doesn't exist";
            } else if (userRepository.existsByEmail(userModel.getEmail())) {
                return "Email already Used";
            }
        } catch (Exception e) {
            return e.toString();
        }
        try {
            User user = new User();
            user.setUserId(userModel.getUserId());
            user.setEmail(userModel.getEmail());
            user.setBranch(userModel.getBranch());
            user.setPassword(passwordEncoder.encode(userModel.getPassword()));
            user.setRole(role);
            user.setFirstName(userModel.getFirstName());
            user.setLastName(userModel.getLastName());
            user.setEnabled(true);
            userRepository.save(user);
            return "User Added Successfully";
        } catch (Exception e) {
            return e.toString();
        }
    }

    @Override
    public String addBranch(BranchModel branchModel) {
        if (branchRepository.existsById(branchModel.getBranchId())) {
            return "Branch Already Exists";
        }
        Branch branch = new Branch();
        branch.setBranchId(branchModel.getBranchId());
        branch.setCity(branchModel.getCity());
        branch.setState(branchModel.getState());
        branch.setActive(true);
        branchRepository.save(branch);
        return "Branch Added Successfully";
    }

    @Override
    public String makeApprover(UserModel userModel) {
        Optional<User> userOptional = userRepository.findById(userModel.getUserId());
        if(userOptional.isEmpty()){
            return "User doesn't exits";
        }
        if (userOptional.get().getRole().equalsIgnoreCase("APPROVER")) {
            return "User is already an approver for " + userOptional.get().getBranch();
        }
        Optional<Branch> branchOptional = branchRepository.findById(userModel.getBranch());
        if (branchOptional.isEmpty()) return "Branch doesn't exist";
        Branch branch = branchOptional.get();
        if (branch.getApprover1() == null) {
            branch.setApprover1(userModel.getUserId());
        } else if (branch.getApprover2() == null) {
            branch.setApprover2(userModel.getUserId());
        } else if (branch.getApprover3() == null) {
            branch.setApprover3(userModel.getUserId());
        } else {
            return "Branch Approver List full";
        }
        User user=userOptional.get();
        user.setRole("APPROVER");
        userRepository.save(user);
        branchRepository.save(branch);
        return "Approver added successfully";
    }

    @Override
    public String changeBranch(UserModel userModel, BranchModel branchModel) {
        Optional<User> optionalUser = userRepository.findById(userModel.getUserId());
        if (optionalUser.isEmpty()) {
            return "User doesn't exist";
        }

        Optional<Branch> optionalBranch = branchRepository.findById(branchModel.getBranchId());
        if (optionalBranch.isEmpty()) {
            return "Branch doesn't exist";
        }

        String remAns = removeIfApprover(userModel);

        // Check if the user's current branch is the same as the new branch
        if (Objects.equals(optionalUser.get().getBranch(), branchModel.getBranchId())) {
            return "User is already associated with the specified branch";
        }

        User user = optionalUser.get();
        user.setBranch(branchModel.getBranchId());
        userRepository.save(user);

        String returnStatement = "Branch of user changed from " + userModel.getBranch() + " to " + branchModel.getBranchId();
        if (remAns != null) {
            returnStatement += "\n" + remAns;
        }

        return returnStatement;
    }

    @Override
    public String disableUser(UserModel userModel) {
        Optional<User> optionalUser = userRepository.findById(userModel.getUserId());
        if (optionalUser.isEmpty()) {
            return "User doesn't exist";
        }

        User user = optionalUser.get();
        user.setEnabled(false);
        userRepository.save(user);

        removeIfApprover(userModel);

        return "User disabled";
    }

    @Override
    public String disableBranch(BranchModel branchModel) {
        Optional<Branch>branchOptional=branchRepository.findById(branchModel.getBranchId());
        if (branchOptional.isEmpty()){
            return "Branch doesn't exits";
        }
        Branch branch=branchOptional.get();
        List<User>userList=userRepository.findAllByBranch(branch.getBranchId());
        for (User user:userList){
            System.out.println(disableUser(new UserModel(user.getUserId(),user.getBranch())));
        }
        branch.setActive(false);
        branchRepository.save(branch);
        return "Branch Disabled Successfully";
    }

    @Override
    public String makeL2Approver(UserModel userModel) {
        Optional<User> optionalUser=userRepository.findById(userModel.getUserId());
        if (optionalUser.isEmpty()){
            return "First add the approver as a normal user";
        }
        User user=optionalUser.get();
        user.setRole("L2APPROVER");
        userRepository.save(user);
        return  userModel.getUserId()+" has been assigned as L2 Approver";
    }

    @Override
    public String removeApprovalRightsL1(UserModel userModel) {
        return removeIfApprover(userModel);
    }

    @Override
    public String removeApprovalRightsL2(UserModel userModel) {
        Optional<User>userOptional=userRepository.findById(userModel.getUserId());
        if (userOptional.isEmpty()){
            return userModel.getUserId()+" doesn't exits";
        }
        User user=userOptional.get();
        if (user.getRole().equalsIgnoreCase("L2APPROVER")){
            user.setRole("USER");
            userRepository.save(user);
            return user.getUserId()+" unassigned from L2 approver.\nNow "+user.getUserId()+" is a normal USER";
        }
        return user.getUserId()+" is not a L2 Approver";
    }

    @Override
    public String makeRectifier(RectifierModel rectifierModel) {
        Optional<User>userOptional=userRepository.findById(rectifierModel.getRectifierId());
        if (userOptional.isEmpty()){
            return rectifierModel.getRectifierId()+" doesn't exits.\nAdd this user as a normal user first then make rectifier";
        }
        Rectifier rectifier=new Rectifier();
        rectifier.setRectifierId(rectifierModel.getRectifierId());
        rectifier.setService(rectifierModel.getService());
        rectifier.setCategory(rectifierModel.getCategory());
        rectifierRepository.save(rectifier);
        return rectifierModel.getRectifierId()+" assigned as rectifier with service as "+rectifier.getService()+" and category of "+rectifier.getCategory();
    }


    public String removeIfApprover(UserModel userModel) {

        Optional<Branch> branchOptional = branchRepository.findById(userModel.getBranch());
        if (branchOptional.isEmpty()) {
            return "Branch doesn't exist";
        }

        Branch branch = branchOptional.get();
        String userId = userModel.getUserId();

        if (userId.equals(branch.getApprover1())) {
            branch.setApprover1(null);
        } else if (userId.equals(branch.getApprover2())) {
            branch.setApprover2(null);
        } else if (userId.equals(branch.getApprover3())) {
            branch.setApprover3(null);
        } else {
            return "The user " + userId + " is not an approver for " + userModel.getBranch();
        }
        branchRepository.save(branch);
        return "The user " + userId + " has been removed as a branch approver for " + userModel.getBranch();
    }
}
