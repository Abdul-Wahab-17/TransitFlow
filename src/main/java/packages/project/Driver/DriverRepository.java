package packages.project.Driver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository <Driver , Integer> {

    @Query("SELECT d.driverId FROM Driver d WHERE d.login.loginId = :loginId")
    Integer getDriverIdbyLoginId(Integer loginId);

    Driver getDriverByDriverId(Integer driverId);

}
