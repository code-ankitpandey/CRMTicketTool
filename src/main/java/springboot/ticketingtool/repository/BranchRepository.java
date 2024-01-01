package springboot.ticketingtool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.ticketingtool.entity.Branch;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch,String> {
}
