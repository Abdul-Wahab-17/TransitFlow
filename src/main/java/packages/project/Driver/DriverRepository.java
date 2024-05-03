package packages.project.Driver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import packages.project.Vehicle.Vehicle;

@Repository
public interface DriverRepository extends JpaRepository <Driver , Integer> {

    @Query("SELECT d.driverId FROM Driver d WHERE d.login.loginId = :loginId")
    Integer getDriverIdbyLoginId( @Param("loginId")Integer loginId);

    Driver getDriverByDriverId(Integer driverId);

    @Query("SELECT d.vehicle from Driver d where d.driverId = :driverId")
    Vehicle getVehicleByDriverId(Integer driverId);

}
