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


    @Column(name= "number")
    String number;
    
    @Column(name="capacity")
    int capacity;

    @ManyToOne
    @JoinColumn(name = "areaId")
    Area area;

    public Vehicle(int vehicleId, String vehicleType, String number, int capacity,Area area) {
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
        this.number = number;
        this.capacity = capacity;
        this.area = area;
    }

    public Vehicle() {}

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
