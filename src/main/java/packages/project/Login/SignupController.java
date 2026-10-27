package packages.project.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import packages.project.Area.Area;
import packages.project.Area.AreaService;
import packages.project.Customer.Customer;
import packages.project.Customer.CustomerService;
import packages.project.Driver.Driver;
import packages.project.Driver.DriverService;
import packages.project.Fee.Fee;
import packages.project.Fee.FeeService;
import packages.project.Salary.Salary;
import packages.project.Salary.SalaryService;
import packages.project.Schedule.Schedule;
import packages.project.Schedule.ScheduleService;
import packages.project.Vehicle.Vehicle;
import packages.project.Vehicle.VehicleService;

@Controller
public class SignupController {
    private final LoginService loginService;
    private final AreaService areaService;
    private final VehicleService vehicleService;
    private final FeeService feeService;
    private final SalaryService salaryService;
    private final CustomerService customerService;
    private final DriverService driverService;
    private final ScheduleService scheduleService;

    @Autowired
    public SignupController(LoginService loginService,
                            AreaService areaService,
                            VehicleService vehicleService,
                            FeeService feeService,
                            SalaryService salaryService,
                            CustomerService customerService,
                            DriverService driverService,
                            ScheduleService scheduleService) {
        this.loginService = loginService;
        this.areaService = areaService;
        this.vehicleService = vehicleService;
        this.feeService = feeService;
        this.salaryService = salaryService;
        this.customerService = customerService;
        this.driverService = driverService;
        this.scheduleService = scheduleService;
    }

    @GetMapping("/signup")
    public String showSignupChoice() {
        return "signup";
    }

    @GetMapping("/signup/customer")
    public String showCustomerSignup(Model model) {
        addSignupOptions(model);
        return "customer_signup";
    }

    @PostMapping("/signup/customer")
    public String signupCustomer(@RequestParam String name,
                                 @RequestParam String phone,
                                 @RequestParam String email,
                                 @RequestParam String address,
                                 @RequestParam int pin,
                                 @RequestParam int areaId,
                                 @RequestParam int vehicleId,
                                 @RequestParam int feeId,
                                 Model model) {
        Login login = createLogin("customer", pin);
        Area area = areaService.getArea(areaId);
        Vehicle vehicle = vehicleService.getVehicle(vehicleId);
        Fee fee = feeService.getFeeById(feeId);

        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhone(Integer.parseInt(phone));
        customer.setEmail(email);
        customer.setAddress(address);
        customer.setLogin(login);
        customer.setArea(area);
        customer.setVehicle(vehicle);
        customer.setFee(fee);
        customer.setPaidStatus(false);
        customerService.save(customer);

        Schedule schedule = new Schedule();
        schedule.setCustomer(customer);
        scheduleService.save(schedule);

        model.addAttribute("role", "customer");
        model.addAttribute("loginId", login.getLoginId());
        return "signup_success";
    }

    @GetMapping("/signup/driver")
    public String showDriverSignup(Model model) {
        addSignupOptions(model);
        return "driver_signup";
    }

    @PostMapping("/signup/driver")
    public String signupDriver(@RequestParam String name,
                               @RequestParam String phone,
                               @RequestParam String email,
                               @RequestParam String address,
                               @RequestParam int pin,
                               @RequestParam int areaId,
                               @RequestParam int vehicleId,
                               @RequestParam int salaryId,
                               Model model) {
        Login login = createLogin("driver", pin);
        Area area = areaService.getArea(areaId);
        Vehicle vehicle = vehicleService.getVehicle(vehicleId);
        Salary salary = salaryService.getSalaryById(salaryId);

        Driver driver = new Driver();
        driver.setName(name);
        driver.setPhone(Integer.parseInt(phone));
        driver.setEmail(email);
        driver.setAddress(address);
        driver.setLogin(login);
        driver.setArea(area);
        driver.setVehicle(vehicle);
        driver.setSalary(salary);
        driver.setSalaryStatus(false);
        driverService.save(driver);

        model.addAttribute("role", "driver");
        model.addAttribute("loginId", login.getLoginId());
        return "signup_success";
    }

    private Login createLogin(String role, int pin) {
        return loginService.createLogin(role, pin);
    }

    private void addSignupOptions(Model model) {
        model.addAttribute("areas", areaService.getAllAreas());
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        model.addAttribute("fees", feeService.getAllFees());
        model.addAttribute("salaries", salaryService.getAllSalaries());
    }
}
