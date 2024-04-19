package packages.project.Driver;

import lombok.Data;
import packages.project.Vehicle.Vehicle;

import javax.persistence.*;

@Data
@Entity
@Table
public class Driver {

    @Id
    int driverId;
    String driver;
    int dphone;
    String address;
    @ManyToOne
    @JoinColumn(name = "vehicleId")
    Vehicle vehicle;

    public Driver(int driverId, String driver, int dphone, String address, Vehicle vehicle) {
        this.driverId = driverId;
        this.driver = driver;
        this.dphone = dphone;
        this.address = address;
        this.vehicle = vehicle;
    }

    public Driver() {
    }
}
