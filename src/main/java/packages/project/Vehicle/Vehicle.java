package packages.project.Vehicle;

import lombok.Data;
import packages.project.Area.Area;

import javax.persistence.*;

@Data
@Entity
@Table
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int vehicleId;

    @Column(name = "vehicle_type")
    String vehicleType;

    @Column(name = "maintenance_costs")
    int maintenanceCost;

    @Column(name= "number")
    String number;
    
    @Column(name="capacity")
    int capacity;

    @Column(name = "rem_cap")
    int remainingCapacity;

    @ManyToOne
    @JoinColumn(name = "areaId")
    Area area;

    public Vehicle(int vehicleId, String vehicleType, int maintenanceCost, String number, int capacity, int remainingCapacity, Area area) {
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
        this.maintenanceCost = maintenanceCost;
        this.number = number;
        this.capacity = capacity;
        this.remainingCapacity = remainingCapacity;
        this.area = area;
    }

    public Vehicle() {}
}
