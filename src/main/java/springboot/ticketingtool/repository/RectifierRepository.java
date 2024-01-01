package springboot.ticketingtool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.ticketingtool.entity.Rectifier;

import java.util.List;

@Repository
public interface RectifierRepository extends JpaRepository<Rectifier,String> {

    List<String> findAllByServiceAndCategory(String service, String category);
}
