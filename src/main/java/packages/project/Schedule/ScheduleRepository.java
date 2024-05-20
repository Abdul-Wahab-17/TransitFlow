package packages.project.Schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository <Schedule , Integer> {


    @Query("select S from Schedule S where S.customerId = :customerId")
    public Schedule getScheduleByCustomerId(Integer customerId);
}
