package packages.project.Customer;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import packages.project.Area.Area;
import packages.project.Fee.Fee;
import packages.project.Login.Login;
import packages.project.Salary.Salary;
import packages.project.Schedule.Schedule;
import packages.project.Vehicle.Vehicle;

import javax.persistence.*;
import java.sql.Time;

@RequiredArgsConstructor
@Data
@Entity
@Table
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "customer_id")
    int customerId;

    @Column(name = "customer_address")
    String address;

    @Column(name = "name")
    String name;

    @Column(name = "phone_number")
    int phone;


    @Column(name = "email_address")
    String email;


    @ManyToOne
    @JoinColumn(name = "areaId")
    Area area;
    @ManyToOne
    @JoinColumn(name = "feeId")
    Fee fee;
    @ManyToOne
    @JoinColumn(name = "vehicleId")
    Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "login_id")
    Login login;

    @Column(name = "paid_status")
    boolean paidStatus;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Schedule schedule;


}
