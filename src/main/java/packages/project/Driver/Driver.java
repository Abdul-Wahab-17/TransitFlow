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
    @GeneratedValue(strategy = GenerationType.AUTO)
    int driverId;


    @Column(name = "name")
    String name;

    @Column(name = "phone_number")
    int phone;

    @Column(name = "email_address")
    String email;

    @Column(name = "salary_status")
    boolean salaryStatus;

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

    public Driver(int driverId, String name, int phone, String email, String address, Area area, Salary salary, Vehicle vehicle,boolean salaryStatus, Login login) {
        this.driverId = driverId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.area = area;
        this.salary = salary;
        this.vehicle = vehicle;
        this.salaryStatus=salaryStatus;
        this.login = login;
    }

    public Driver() {
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
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

    public boolean isSalaryStatus() {
        return salaryStatus;
    }

    public void setSalaryStatus(boolean salaryStatus) {
        this.salaryStatus = salaryStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
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
}
