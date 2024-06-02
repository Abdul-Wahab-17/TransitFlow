package packages.project.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
import packages.project.Salary.Salary;
import packages.project.Salary.SalaryService;
import packages.project.Schedule.Schedule;
import packages.project.Schedule.ScheduleService;
import packages.project.Vehicle.Vehicle;
import packages.project.Vehicle.VehicleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.annotation.WebServlet;

@Controller
class AdminController {
  // @Autowired
   //JwtTokenUtil jwtTokenUtil;

    private final AdminService adminService;
    private final CustomerService customerService;
    private final DriverService driverService;
    private final LoginService loginService;
    private final AreaService areaService;
    private final FeeService feeService;
    private final VehicleService vehicleService;
    private final SalaryService salaryService;
    private final ScheduleService scheduleService;



    @Autowired
    public AdminController(AdminService adminService, CustomerService customerService , DriverService driverService ,
                           LoginService loginService , AreaService areaService , FeeService feeService , VehicleService vehicleService ,
                           SalaryService salaryService , ScheduleService scheduleService){
        this.adminService=adminService;
        this.customerService=customerService;
        this.driverService=driverService;
        this.loginService=loginService;
        this.areaService=areaService;
        this.feeService=feeService;
        this.vehicleService=vehicleService;
        this.salaryService=salaryService;
        this.scheduleService=scheduleService;
    }


    @GetMapping("/api/admin/{loginId}")
    public String redirectToAdminDashboard(@PathVariable Long loginId) {
        // Construct the redirect URL
        String redirectUrl = "/admin/" + loginId;

        // Redirect to the specified URL
        return "redirect:" + redirectUrl;
    }
    @GetMapping("/admin/{loginId}")
    public String getAdminDashboard(@PathVariable Integer loginId, Model model) {
        Admin admin = adminService.getAdmin(loginId);
        model.addAttribute("admin", admin);

        // Fetch driver data from the database
        List<Driver> drivers = adminService.getAllDrivers();
        model.addAttribute("drivers", drivers);

        List<Customer> customers = adminService.getAllCustomers();
        model.addAttribute("customers" , customers);

        List<Vehicle> vehicles = adminService.getAllVehicles();
        model.addAttribute("vehicles" , vehicles);

        //  model.addAttribute("loginId", loginId);
        // Initialize driversVisible attribute to false initially
        model.addAttribute("driversVisible", false);
        model.addAttribute("customersVisible" , false);
        model.addAttribute("vehiclesVisible" , false);
        return "admin_dashboard";
    }

/*

 @GetMapping("/admin/{loginId}")
 @PreAuthorize("hasRole('admin')")
 public String getAdminDashboard(@PathVariable Integer loginId, Model model, HttpServletRequest request) {
 
    String token = jwtTokenUtil.resolveToken(request);
   System.out.println("The token12345 is: "+ token);
    if (token != null && jwtTokenUtil.validateToken(token)) {
       // Token is valid, set up authentication
        UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

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
     } else {
         // Token is invalid, handle accordingly (e.g., return unauthorized error)
         return "redirect:/unauthorized";
    }
 }

*/


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
        List <Vehicle> vehicles = adminService.getAllVehicles();
        List<Area> areas = areaService.getAllAreas();
        // Pass driver object to the view for admin editing
        model.addAttribute("vehicles" , vehicles);
        model.addAttribute("areas" , areas);
        model.addAttribute("driver", driver);
        return "admin_driver";
    }

    @GetMapping("/editCustomer")
    public String editCustomer(@RequestParam String loginId, Model model) {
        // Retrieve customer information by loginId
        Customer customer = customerService.getCustomer(Integer.parseInt(loginId));
        List <Vehicle> vehicles = adminService.getAllVehicles();
        List<Area> areas = areaService.getAllAreas();
        List<Fee> fees = feeService.getAllFees();
        model.addAttribute("fees" , fees);
        model.addAttribute("vehicles" , vehicles);
        model.addAttribute("areas" , areas);
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

    @PostMapping("/addDriver")
    public String addDriver(@RequestParam String driverName,
                            @RequestParam String driverPhone,
                            @RequestParam String driverAddress,
                            @RequestParam String driverEmail,
                            @RequestParam int areaId,
                            @RequestParam int vehicleId,
                            @RequestParam int salaryId,
                            @RequestParam boolean salaryStatus,
                            Model model) {

        // Generate a random pin for login
        String pin = generatePin();

        // Create a new Login entity
        Login login = new Login();
        login.setRole("driver");
        login.setPin(Integer.parseInt(pin));
        loginService.save(login);

        // Retrieve Area and Vehicle entities from their respective services
        Area area = areaService.getArea(areaId);
        Vehicle vehicle = vehicleService.getVehicle(vehicleId);
        Salary salary = salaryService.getSalaryById(salaryId);

        // Create a new Driver entity
        Driver driver = new Driver();
        driver.setName(driverName);
        driver.setPhone(Integer.parseInt(driverPhone));
        driver.setAddress(driverAddress);
        driver.setEmail(driverEmail);
        driver.setArea(area);
        driver.setSalaryStatus(salaryStatus);
        driver.setVehicle(vehicle);
        driver.setSalary(salary);
        driver.setLogin(login);

        // Save the Driver entity
        driverService.save(driver);

        // Add attributes to the model for the view
        model.addAttribute("loginId", login.getLoginId());
        model.addAttribute("password", pin);

        // Return the view name
        return "driverAdded";
    }

    @PostMapping("/addVehicle")
    public String addVehicle(@RequestParam String vehicleType, @RequestParam String vehicleNumber,
                             @RequestParam int capacity,
                             @RequestParam int areaId) {

        Area area = areaService.getArea(areaId);

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleType(vehicleType);
        vehicle.setNumber(vehicleNumber);
        vehicle.setCapacity(capacity);
        vehicle.setArea(area);

        vehicleService.save(vehicle);

    //    String redirectUrl = "redirect:/admin/" + loginId;
        return "redirect:/admin/addvehicle";
    }




    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam int loginId, RedirectAttributes redirectAttributes , Model model) {
        String newPassword = generatePin();
        Login login = loginService.findByLoginId(loginId);
        login.setPin(Integer.parseInt(newPassword));
        loginService.save(login);
        redirectAttributes.addFlashAttribute("newPassword", newPassword);
        model.addAttribute("newPassword", newPassword);
        model.addAttribute("loginId", loginId);
        return "resetPasswordSuccess";
    }

    @PostMapping("/addCustomer")
    public String addCustomer(@RequestParam String customerName, @RequestParam String customerPhone,
                              @RequestParam String customerAddress, @RequestParam String customerEmail,
                              @RequestParam int areaId, @RequestParam int vehicleId,
                              @RequestParam int feeId, @RequestParam boolean paidStatus, Model model) {

        // Step 1: Generate PIN
        String pin = generatePin();

        // Step 2: Save Login
        Login login = new Login();
        login.setRole("customer");
        login.setPin(Integer.parseInt(pin));
        loginService.save(login);

        // Step 3: Fetch Area, Fee, and Vehicle
        Area area = areaService.getArea(areaId);
        Fee fee = feeService.getFeeById(feeId);
        Vehicle vehicle = vehicleService.getVehicle(vehicleId);

        // Step 4: Save Customer
        Customer customer = new Customer();
        customer.setName(customerName);
        customer.setPhone(Integer.parseInt(customerPhone));
        customer.setAddress(customerAddress);
        customer.setEmail(customerEmail);
        customer.setLogin(login);
        customer.setArea(area);
        customer.setFee(fee);
        customer.setPaidStatus(paidStatus);
        customer.setVehicle(vehicle);
        customerService.save(customer);

        // Step 5: Save Schedule
        Schedule schedule = new Schedule();
        schedule.setMondayMorning(false);
        schedule.setMondayEvening(false);
        schedule.setTuesdayMorning(false);
        schedule.setTuesdayEvening(false);
        schedule.setWednesdayMorning(false);
        schedule.setWednesdayEvening(false);
        schedule.setThursdayMorning(false);
        schedule.setThursdayEvening(false);
        schedule.setFridayMorning(false);
        schedule.setFridayEvening(false);
        schedule.setCustomer(customer);
        scheduleService.save(schedule);

        // Step 6: Prepare Model
        model.addAttribute("loginId", login.getLoginId());
        model.addAttribute("password", pin);

        // Step 7: Redirect to the customer added page
        return "customerAdded";
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

    @GetMapping("/getFeesByArea")
    public ResponseEntity<?> getFeesByArea(@RequestParam int areaId) {
        // Fetch fees by areaId
        try {
            List<Fee> fees = feeService.getFee(areaId);
            return new ResponseEntity<>(fees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch fees by area", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getSalariesByArea")
    public ResponseEntity<?> getSalariesByArea(@RequestParam int areaId) {
        try {
            List<Salary> salaries = salaryService.getSalaryForArea(areaId);
            return new ResponseEntity<>(salaries, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch salaries by area", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/admin/editCustomer")
    public String editCustomerInfo(@RequestParam String loginId,
                                   @RequestParam String areaId,
                                   @RequestParam String vehicleId,
                                   @RequestParam int feeId,
                                   @RequestParam boolean paidStatus) {

        // Retrieve the customer and related objects
        Customer customer = customerService.getCustomer(Integer.parseInt(loginId));
        Vehicle vehicle = vehicleService.getVehicle(Integer.parseInt(vehicleId));
        Fee fee = feeService.getFeeById(feeId);

        // Update customer information
        customer.setArea(areaService.getArea(Integer.parseInt(areaId))); // Set the area based on areaId
        customer.setVehicle(vehicle);
        customer.setFee(fee);
        customer.setPaidStatus(paidStatus);

        // Save updated customer
        customerService.save(customer);

        // Redirect to the customer details page
        return "redirect:/customers/" + loginId;
    }

    @PostMapping("/admin/editDriver")
    public String editDriverInfo(@RequestParam String  loginId, @RequestParam String areaId, @RequestParam String vehicleId,
                                 @RequestParam int salaryId , @RequestParam boolean salaryStatus) {

            Driver driver = driverService.getDriver(Integer.parseInt(loginId));
            Area area = areaService.getArea(Integer.parseInt(areaId));
            Vehicle vehicle = vehicleService.getVehicle(Integer.parseInt(vehicleId));
            Salary salary = salaryService.getSalaryById(salaryId);

            // Update driver details
            driver.setArea(area);
            driver.setVehicle(vehicle);
            driver.setSalary(salary);
            driver.setSalaryStatus(salaryStatus);

            driverService.save(driver);

            return "redirect:/driver/" + loginId;
    }

    @GetMapping("/admin/pendingFee")
    public String showPendingFeeCustomers(Model model) {
        // Get pending fee customers
        model.addAttribute("pendingFeeCustomers", customerService.findCustomersWithPendingFee());
        return "pendingFee";
    }

    @GetMapping("/admin/pendingSalary")
    public String showPendingSalaryDrivers(Model model) {
        // Get pending salary drivers
        model.addAttribute("pendingSalaryDrivers", driverService.findPendingSalary());
        return "pendingSalary";
    }

    @GetMapping("/admin/addcustomer")
    public String getAddCustomerPage(Model model){
        model.addAttribute("areas", areaService.getAllAreas());
        model.addAttribute( "fees" , feeService.getAllFees());
        model.addAttribute("vehicles" , vehicleService.getAllVehicles());
        return "addcustomer";
    }
    @GetMapping("/admin/adddriver")
    public String getAddDriverPage(Model model){
        model.addAttribute("areas", areaService.getAllAreas());
        model.addAttribute("vehicles" , vehicleService.getAllVehicles());
        model.addAttribute("salaries" , salaryService.getAllSalaries());
        return "adddriver";
    }
    @GetMapping("/admin/addvehicle")
    public String getAddVehiclePage(Model model){
        model.addAttribute("areas", areaService.getAllAreas());
        return "addvehicle";
    }



}
