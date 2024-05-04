package packages.project.Vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    @Query("SELECT v FROM Vehicle v WHERE v.area.areaId = :areaId OR (v.area IS NULL AND :areaId IS NULL)")
    public List<Vehicle> getVehicleByAreaId(Integer areaId);

}
