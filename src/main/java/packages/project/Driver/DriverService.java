package packages.project.Driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import packages.project.Admin.Admin;
import packages.project.Vehicle.Vehicle;

@Service
public class DriverService {

    DriverRepository driverRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository){
        this.driverRepository=driverRepository;
    }
    public Driver getDriver(Integer loginId){
        Driver driver = driverRepository.getDriverByDriverId(driverRepository.getDriverIdbyLoginId(loginId));
        return driver;
    }
    public void save(Driver existingDriver) {
        driverRepository.save(existingDriver);
    }

    public Integer findDriverId(Integer loginID){
        Integer loginId = driverRepository.getDriverIdbyLoginId(loginID);
        return loginId;
    }

    public Vehicle findVehicle(Integer driverId){
        Vehicle vehicle = driverRepository.getVehicleByDriverId(driverId);
        return vehicle;
    }
}
