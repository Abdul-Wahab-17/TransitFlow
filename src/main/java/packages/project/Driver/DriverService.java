package packages.project.Driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import packages.project.Admin.Admin;
import packages.project.Vehicle.Vehicle;

import java.util.List;

@Service
public class DriverService {

    DriverRepository driverRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository){
        this.driverRepository=driverRepository;
    }
    public Driver getDriver(Integer loginId){
        Integer driverId = driverRepository.getDriverIdByLoginId(loginId);
        if (driverId == null) {
            return null;
        }
        return driverRepository.getDriverByDriverId(driverId);
    }
    public void save(Driver existingDriver) {
        driverRepository.save(existingDriver);
    }

    public Integer findDriverId(Integer loginID){
        Integer loginId = driverRepository.getDriverIdByLoginId(loginID);
        return loginId;
    }

    public Vehicle findVehicle(Integer driverId){
        Vehicle vehicle = driverRepository.getVehicleByDriverId(driverId);
        return vehicle;
    }
    public List<Driver> findPendingSalary(){
        return driverRepository.getDriverByPendingSalary();
    }


    public Driver getDriverByVehicleId(Integer vehicleId){
        return driverRepository.getDriverByVehicleId(vehicleId);
    }
}
