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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Fee getFee() {
        return fee;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public boolean isPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(boolean paidStatus) {
        this.paidStatus = paidStatus;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
