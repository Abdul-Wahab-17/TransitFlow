package packages.project.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import packages.project.Area.Area;
import packages.project.Area.AreaService;
import packages.project.Customer.Customer;
import packages.project.Customer.CustomerService;
import packages.project.Driver.Driver;
import packages.project.Driver.DriverService;
import packages.project.Fee.Fee;
import packages.project.Fee.FeeService;
import packages.project.Login.Login;
import packages.project.Login.LoginService;
import packages.project.Salary.SalaryService;
import packages.project.Vehicle.Vehicle;
import packages.project.Vehicle.VehicleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class AdminController {

    private final AdminService adminService;
    private final CustomerService customerService;
    private final DriverService driverService;
    private final LoginService loginService;
    private final AreaService areaService;
    private final FeeService feeService;
    private final VehicleService vehicleService;
    private final SalaryService salaryService;
    @Autowired
    public AdminController(AdminService adminService, CustomerService customerService , DriverService driverService ,
                           LoginService loginService , AreaService areaService , FeeService feeService , VehicleService vehicleService , SalaryService salaryService){
        this.adminService=adminService;
        this.customerService=customerService;
        this.driverService=driverService;
        this.loginService=loginService;
        this.areaService=areaService;
        this.feeService=feeService;
        this.vehicleService=vehicleService;
        this.salaryService=salaryService;
    }
    @PostMapping("/api/admin/{loginId}")
   @PreAuthorize("hasRole('admin')")
    public String redirectToAdminDashboard(@PathVariable Long loginId) {
        // Construct the redirect URL
        String redirectUrl = "/admin/" + loginId;

        // Redirect to the specified URL
        return "redirect:" + redirectUrl;
    }


    @GetMapping("/admin/{loginId}")
   @PreAuthorize("hasRole('admin')")
    public String getAdminDashboard(@PathVariable Integer loginId, Model model) {
        Admin admin = adminService.getAdmin(loginId);
        model.addAttribute("admin", admin);

        // Fetch driver data from the database
        List<Driver> drivers = adminService.getAllDrivers();
        model.addAttribute("drivers", drivers);

        List<Customer> customers = adminService.getAllCustomers();
        model.addAttribute("customers", customers);

        List<Vehicle> vehicles = adminService.getAllVehicles();
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("areas", areaService.getAllAreas());
        model.addAttribute("driversVisible", false);
        model.addAttribute("customersVisible", false);
        model.addAttribute("vehiclesVisible", false);
        return "admin_dashboard";
    }

    @GetMapping("/searchDrivers")
    public String searchDrivers(@RequestParam String loginId, Model model) {
        try {
            List<Driver> drivers = (List<Driver>) driverService.getDriver(Integer.parseInt(loginId));
            model.addAttribute("drivers", drivers);
            return "admin_dashboard";
        } catch (NumberFormatException e) {
            // Handle invalid input
            return "redirect:/admin/{loginId}";
        }
    }

    @GetMapping("/searchCustomers")
    public String searchCustomers(@RequestParam String loginId, Model model) {
        try {
            List<Customer> customers = (List<Customer>) customerService.getCustomer(Integer.parseInt(loginId));
            model.addAttribute("customers", customers);
            return "admin_dashboard";
        } catch (NumberFormatException e) {
            // Handle invalid input
            return "redirect:/admin/{loginId}";
        }
    }

    @GetMapping("/editDriver")
    public String editDriver(@RequestParam String loginId, Model model) {
        // Retrieve driver information by loginId
        Driver driver = driverService.getDriver(Integer.parseInt(loginId));
        // Pass driver object to the view for admin editing
        model.addAttribute("driver", driver);
        return "admin_driver";
    }

    @GetMapping("/editCustomer")
    public String editCustomer(@RequestParam String loginId, Model model) {
        // Retrieve customer information by loginId
        Customer customer = customerService.getCustomer(Integer.parseInt(loginId));
        // Pass customer object to the view for admin editing
        model.addAttribute("customer", customer);
        return "admin_customer";
    }

    @GetMapping("/editVehicle")
    public String editVehicle(@RequestParam String vehicleNumber, Model model) {
        // Retrieve vehicle information by vehicleNumber
        Vehicle vehicle = vehicleService.getVehicleByNumber(vehicleNumber);
        // Pass vehicle object to the view for admin editing
        model.addAttribute("vehicle", vehicle);
        return "admin_vehicle";
    }

    @GetMapping("/searchVehicles")
    public String searchVehicles(@RequestParam String vehicleNumber, Model model) {
        try {
            Vehicle vehicle = vehicleService.getVehicleByNumber(vehicleNumber);
            List<Vehicle> vehicles = new ArrayList<>();
            if(vehicle != null) {
                vehicles.add(vehicle);
            }
            model.addAttribute("vehicles", vehicles);
            return "admin_dashboard";
        } catch (Exception e) {
            // Handle exception
            return "redirect:/admin/{loginId}";
        }
    }


    @PostMapping("/toggleDrivers")
    public String toggleDrivers(Model model) {
        // Toggle the visibility flag for drivers table
        boolean driversVisible = !(boolean) model.getAttribute("driversVisible");
        model.addAttribute("driversVisible", driversVisible);

        List<Driver> drivers = adminService.getAllDrivers();

        model.addAttribute("drivers", drivers);

        return "admin_dashboard";
    }
    private int generateLoginId() {
        Random random = new Random(System.currentTimeMillis());
        return Integer.parseInt(String.valueOf(random.nextInt(10000)));
    }

    @PostMapping("/toggleVehicles")
    public String toggleVehicles(Model model){
        boolean vehicleVisible = !(boolean) model.getAttribute("vehicleVisible");
        model.addAttribute("vehicleVisible", vehicleVisible);

        List<Vehicle> vehicles = adminService.getAllVehicles();

        model.addAttribute("vehicles", vehicles);

        return "admin_dashboard";
    }

    @PostMapping("/toggleCustomers")
    public String toggleCustomers(Model model) {
        boolean customersVisible = !(boolean) model.getAttribute("customersVisible");
        model.addAttribute("customersVisible", customersVisible);

        List<Customer> customers = adminService.getAllCustomers();

        model.addAttribute("customers", customers);

        return "admin_dashboard";
    }

    private String generatePin() {
        Random random = new Random();
        return String.valueOf(random.nextInt(10000));
    }

    public void addCustomers(String name, String phone, String address, String email) {
        int loginId = generateLoginId();
        String pin = generatePin();

        Login login = new Login();
        login.setLoginId(loginId);
        login.setRole("customer");
        login.setPin(Integer.parseInt(pin));
        loginService.save(login);

        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhone(Integer.parseInt(phone));
        customer.setAddress(address);
        customer.setEmail(email);
        customer.setLogin(login);
        customerService.save(customer);
    }

    @PostMapping("/addDriver")
    public String addDriver(@RequestParam String driverName, @RequestParam int driverPhone,
                            @RequestParam String driverAddress, @RequestParam String driverEmail,
                            @RequestParam int areaId, @RequestParam int loginId, 
                            @RequestParam int vehicleId, Model model) {

        String pin = generatePin();

        Login login = new Login();
        login.setRole("driver");
        login.setPin(Integer.parseInt(pin));
        loginService.save(login);

        Area area = areaService.getArea(areaId);
        Vehicle vehicle = vehicleService.getVehicle(vehicleId);

        Driver driver = new Driver();
        driver.setName(driverName);
        driver.setPhone(driverPhone);
        driver.setAddress(driverAddress);
        driver.setEmail(driverEmail);
        driver.setArea(area);
        driver.setVehicle(vehicle);
        driver.setSalary(salaryService.getSalaryForArea(area.getAreaId()));
        driver.setLogin(login);

        driverService.save(driver);

        String redirectUrl = "redirect:/admin/" + loginId;
        return redirectUrl;
    }

    @PostMapping("/addVehicle")
    public String addVehicle(@RequestParam String vehicleType, @RequestParam String vehicleNumber,
                             @RequestParam int capacity, @RequestParam int remainingCapacity,
                             @RequestParam int areaId, @RequestParam int loginId, Model model) {

        Area area = areaService.getArea(areaId);

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleType(vehicleType);
        vehicle.setNumber(vehicleNumber);
        vehicle.setCapacity(capacity);
        vehicle.setRemainingCapacity(remainingCapacity);
        vehicle.setArea(area);

        vehicleService.save(vehicle);

        String redirectUrl = "redirect:/admin/" + loginId;
        return redirectUrl;
    }




    @PostMapping("/addCustomer")
    public String addCustomer(@RequestParam String customerName, @RequestParam String customerPhone,
                              @RequestParam String customerAddress, @RequestParam String customerEmail,
                              @RequestParam int loginId, @RequestParam int areaId , @RequestParam int vehicleId, Model model) {

        String pin = generatePin();

        Login login = new Login();
        login.setRole("customer");
        login.setPin(Integer.parseInt(pin));
        loginService.save(login);

        Area area = areaService.getArea(areaId);
        Fee fee = feeService.getFee(areaId);
        Vehicle vehicle = vehicleService.getVehicle(vehicleId);

        Customer customer = new Customer();
        customer.setName(customerName);
        customer.setPhone(Integer.parseInt(customerPhone));
        customer.setAddress(customerAddress);
        customer.setEmail(customerEmail);
        customer.setLogin(login);
        customer.setArea(area);
        customer.setFee(fee);
        customer.setVehicle(vehicle);
        customerService.save(customer);

        String redirectUrl = "redirect:/admin/" + loginId;
        return redirectUrl;
    }

    @GetMapping("/getVehiclesByArea")
    public ResponseEntity<?> getVehiclesByArea(@RequestParam int areaId) {
        // Fetch vehicles by areaId
        try {
            List<Vehicle> vehicles = vehicleService.getVehiclesForArea(areaId);
            return new ResponseEntity<>(vehicles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch vehicles by area", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
