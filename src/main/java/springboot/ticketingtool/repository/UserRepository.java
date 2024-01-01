package springboot.ticketingtool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.ticketingtool.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    boolean existsById(String userId);

    boolean existsByEmail(String email);

    List<User> findAllByBranch(String branchId);
}
