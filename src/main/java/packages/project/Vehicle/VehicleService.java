package packages.project.Vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> getVehiclesForArea( Integer areaId){
        return vehicleRepository.getVehicleByAreaId(areaId);
    }

    public Vehicle getVehicle(Integer vehicleId){
        return vehicleRepository.getVehicleByVehicleId(vehicleId);
    }

    public void save(Vehicle vehicle){vehicleRepository.save(vehicle);}

    public Vehicle getVehicleByNumber(String number){ return vehicleRepository.getVehicleByNumber(number);}

    public List<Vehicle> getAllVehicles(){
        return vehicleRepository.findAll();
    }
}
