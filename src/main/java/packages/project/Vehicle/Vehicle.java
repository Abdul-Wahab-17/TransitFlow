package packages.project.Vehicle;

import lombok.Data;
import packages.project.Area.Area;

import javax.persistence.*;

@Data
@Entity
@Table
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int vehicleId;

    @Column(name = "vehicle_type")
    String vehicleType;

    @Column(name = "maintenance_costs")
    int maintenanceCost;
    String number;
    int capacity;

    @Column(name = "rem_cap")
    int remainingCapacity;

    @ManyToOne
    @JoinColumn(name = "areaId")
    Area area;

    public Vehicle(int vehicleId, String number, int capacity, int remainingCapacity) {
        this.vehicleId = vehicleId;
        this.number = number;
        this.capacity = capacity;
        this.remainingCapacity = remainingCapacity;
    }

    public Vehicle() {}
}
