package packages.project.Driver;

import lombok.Data;
import packages.project.Area.Area;
import packages.project.Login.Login;
import packages.project.Salary.Salary;
import packages.project.Vehicle.Vehicle;

import javax.persistence.*;

@Data
@Entity
@Table
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int driverId;

    @Column(name = "name")
    String name;

    @Column(name = "phone_number")
    int phone;

    @Column(name = "email_address")
    String email;

    @Column(name = "driver_address")
    String address;
    @ManyToOne
    @JoinColumn(name = "areaId")
    Area area;
    @ManyToOne
    @JoinColumn(name = "salaryId")
    Salary salary;
    @ManyToOne
    @JoinColumn(name = "vehicleId")
    Vehicle vehicle;
    @ManyToOne
    @JoinColumn(name = "login_id")
    Login login;
    public Driver(int driverId, String name, int phone, String address, Vehicle vehicle , Login login) {
        this.driverId = driverId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.vehicle = vehicle;
        this.login=login;
    }

    public Driver() {
    }
}
