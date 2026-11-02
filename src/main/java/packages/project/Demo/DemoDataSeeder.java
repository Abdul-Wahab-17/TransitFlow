package packages.project.Demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import packages.project.Admin.Admin;
import packages.project.Admin.AdminRepository;
import packages.project.Area.Area;
import packages.project.Area.AreaRepository;
import packages.project.Customer.Customer;
import packages.project.Customer.CustomerRepository;
import packages.project.Driver.Driver;
import packages.project.Driver.DriverRepository;
import packages.project.Fee.Fee;
import packages.project.Fee.FeeRepository;
import packages.project.Login.Login;
import packages.project.Login.LoginService;
import packages.project.Salary.Salary;
import packages.project.Salary.SalaryRepository;
import packages.project.Schedule.Schedule;
import packages.project.Schedule.ScheduleRepository;
import packages.project.Vehicle.Vehicle;
import packages.project.Vehicle.VehicleRepository;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Fills an empty database with a small demo fleet so the app can be explored straight away.
 * Active only with the "demo" Spring profile (the Docker setup turns it on). It never touches a
 * database that already has data. Demo logins and PINs are printed to the log.
 */
@Component
@Profile("demo")
public class DemoDataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DemoDataSeeder.class);
    public static final int ADMIN_PIN = 1111, DRIVER_PIN = 2222, CUSTOMER_PIN = 3333;

    private final AreaRepository areas;
    private final VehicleRepository vehicles;
    private final FeeRepository fees;
    private final SalaryRepository salaries;
    private final DriverRepository drivers;
    private final CustomerRepository customers;
    private final AdminRepository admins;
    private final ScheduleRepository schedules;
    private final LoginService logins;

    public DemoDataSeeder(AreaRepository areas, VehicleRepository vehicles, FeeRepository fees,
                          SalaryRepository salaries, DriverRepository drivers, CustomerRepository customers,
                          AdminRepository admins, ScheduleRepository schedules, LoginService logins) {
        this.areas = areas;
        this.vehicles = vehicles;
        this.fees = fees;
        this.salaries = salaries;
        this.drivers = drivers;
        this.customers = customers;
        this.admins = admins;
        this.schedules = schedules;
        this.logins = logins;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (areas.count() > 0) {
            log.info("Demo data skipped: database already has data");
            return;
        }

        Area f7 = area("F-7 Islamabad", 22, 40);
        Area g10 = area("G-10 Islamabad", 18, 35);
        Area saddar = area("Saddar Rawalpindi", 30, 55);

        Fee feeF7 = fee(9500, f7);
        Fee feeG10 = fee(8500, g10);
        Fee feeSaddar = fee(11000, saddar);
        Salary salF7 = salary(48000, f7);
        Salary salG10 = salary(46000, g10);
        Salary salSaddar = salary(52000, saddar);

        Vehicle bus1 = vehicle("Bus", "ICT-4821", 40, f7);
        Vehicle coaster = vehicle("Coaster", "ICT-3377", 26, g10);
        Vehicle bus2 = vehicle("Bus", "RIA-9054", 40, saddar);

        Login adminLogin = logins.createLogin("admin", ADMIN_PIN);
        Admin admin = new Admin();
        admin.setName("Demo Admin");
        admin.setPhone("03001230001");
        admin.setEmail("admin@transitflow.demo");
        admin.setAddress("Transport Office, H-12 Islamabad");
        admin.setLogin(adminLogin);
        admins.save(admin);

        Login d1 = logins.createLogin("driver", DRIVER_PIN);
        driver("Muhammad Imran", "03001230002", "imran@transitflow.demo", "House 12, Street 4, F-7", f7, salF7, bus1, d1, true);
        Login d2 = logins.createLogin("driver", DRIVER_PIN);
        driver("Tariq Mehmood", "03001230003", "tariq@transitflow.demo", "House 8, Street 11, G-10", g10, salG10, coaster, d2, false);
        Login d3 = logins.createLogin("driver", DRIVER_PIN);
        driver("Nadeem Akhtar", "03001230004", "nadeem@transitflow.demo", "Gali 3, Saddar", saddar, salSaddar, bus2, d3, true);

        Login c1 = logins.createLogin("customer", CUSTOMER_PIN);
        customer("Ayesha Khan", "03111230005", "ayesha@transitflow.demo", "Flat 4, F-7 Markaz", f7, feeF7, bus1, c1, true, true);
        Login c2 = logins.createLogin("customer", CUSTOMER_PIN);
        customer("Hamza Ali", "03111230006", "hamza@transitflow.demo", "House 21, G-10/2", g10, feeG10, coaster, c2, false, false);
        Login c3 = logins.createLogin("customer", CUSTOMER_PIN);
        customer("Sana Malik", "03111230007", "sana@transitflow.demo", "House 5, Saddar Road", saddar, feeSaddar, bus2, c3, true, true);

        log.info("==================== TransitFlow demo accounts ====================");
        log.info("  Admin    login ID {}  PIN {}", adminLogin.getLoginId(), ADMIN_PIN);
        log.info("  Driver   login IDs {}, {}, {}  PIN {}", d1.getLoginId(), d2.getLoginId(), d3.getLoginId(), DRIVER_PIN);
        log.info("  Customer login IDs {}, {}, {}  PIN {}", c1.getLoginId(), c2.getLoginId(), c3.getLoginId(), CUSTOMER_PIN);
        log.info("===================================================================");
    }

    private Area area(String name, int km, int population) {
        Area a = new Area();
        a.setAreaName(name);
        a.setDistanceFromNust(km);
        a.setCustomerPopulation(population);
        return areas.save(a);
    }

    private Fee fee(int amount, Area area) {
        Fee f = new Fee();
        f.setAmount(amount);
        f.setDueDate(Date.valueOf(LocalDate.now().plusDays(10)));
        f.setArea(area);
        return fees.save(f);
    }

    private Salary salary(int amount, Area area) {
        Salary s = new Salary();
        s.setAmount(amount);
        s.setArea(area);
        return salaries.save(s);
    }

    private Vehicle vehicle(String type, String number, int capacity, Area area) {
        Vehicle v = new Vehicle();
        v.setVehicleType(type);
        v.setNumber(number);
        v.setCapacity(capacity);
        v.setArea(area);
        return vehicles.save(v);
    }

    private void driver(String name, String phone, String email, String address, Area area, Salary salary,
                        Vehicle vehicle, Login login, boolean salaryPaid) {
        Driver d = new Driver();
        d.setName(name);
        d.setPhone(phone);
        d.setEmail(email);
        d.setAddress(address);
        d.setArea(area);
        d.setSalary(salary);
        d.setVehicle(vehicle);
        d.setLogin(login);
        d.setSalaryStatus(salaryPaid);
        drivers.save(d);
    }

    private void customer(String name, String phone, String email, String address, Area area, Fee fee,
                          Vehicle vehicle, Login login, boolean paid, boolean fullWeek) {
        Customer c = new Customer();
        c.setName(name);
        c.setPhone(phone);
        c.setEmail(email);
        c.setAddress(address);
        c.setArea(area);
        c.setFee(fee);
        c.setVehicle(vehicle);
        c.setLogin(login);
        c.setPaidStatus(paid);
        customers.save(c);

        Schedule s = new Schedule();
        s.setCustomer(c);
        s.setMondayMorning(true);
        s.setTuesdayMorning(true);
        s.setWednesdayMorning(fullWeek);
        s.setThursdayMorning(true);
        s.setFridayMorning(true);
        s.setMondayEvening(true);
        s.setTuesdayEvening(fullWeek);
        s.setWednesdayEvening(fullWeek);
        s.setThursdayEvening(true);
        s.setFridayEvening(false);
        schedules.save(s);
    }
}
