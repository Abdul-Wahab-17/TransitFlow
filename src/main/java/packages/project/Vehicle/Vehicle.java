package packages.project.Vehicle;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table
public class Vehicle {

    @Id
    int vehicleId;
    String number;
    int capacity;
    int remainingCapacity;

    public Vehicle(int vehicleId, String number, int capacity, int remainingCapacity) {
        this.vehicleId = vehicleId;
        this.number = number;
        this.capacity = capacity;
        this.remainingCapacity = remainingCapacity;
    }

    public Vehicle() {}
}
