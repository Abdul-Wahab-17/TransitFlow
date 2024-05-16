package packages.project.Driver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import packages.project.Vehicle.Vehicle;

@Repository
public interface DriverRepository extends JpaRepository <Driver , Integer> {

    @Query("SELECT d.driverId FROM Driver d WHERE d.login.loginId = :loginId")
    Integer getDriverIdByLoginId( @Param("loginId")Integer loginId);

    @Query("select D from Driver D where D.driverId=:driverId")
    Driver getDriverByDriverId(Integer driverId);

    @Query("SELECT d.vehicle from Driver d where d.driverId = :driverId")
    Vehicle getVehicleByDriverId(Integer driverId);

    @Query("select D from Driver D where D.vehicle.vehicleId = :vehicleId")
    Driver getDriverByVehicleId(Integer vehicleId);

}
