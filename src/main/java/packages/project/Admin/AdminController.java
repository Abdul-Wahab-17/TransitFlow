package packages.project.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import packages.project.Customer.Customer;
import packages.project.Customer.CustomerController;
import packages.project.Customer.CustomerService;
import packages.project.Driver.Driver;
import packages.project.Driver.DriverService;
import packages.project.Login.Login;
import packages.project.Login.LoginService;
import packages.project.Vehicle.Vehicle;

import java.util.List;
import java.util.Random;

@Controller
public class AdminController {

    private final AdminService adminService;
    private final CustomerService customerService;
    private final DriverService driverService;
    private final LoginService loginService;
    @Autowired
    public AdminController(AdminService adminService, CustomerService customerService , DriverService driverService , LoginService loginService){
        this.adminService=adminService;
        this.customerService=customerService;
        this.driverService=driverService;
        this.loginService=loginService;
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

        // Initialize driversVisible attribute to false initially
        model.addAttribute("driversVisible", false);
        model.addAttribute("customersVisible" , false);
        model.addAttribute("vehiclesVisible" , false);
        return "admin_dashboard";
    }


    @PostMapping("/toggleDrivers")
    public String toggleDrivers(Model model) {
        // Toggle the visibility flag for drivers table
        boolean driversVisible = !(boolean) model.getAttribute("driversVisible");
        model.addAttribute("driversVisible", driversVisible);

        // Fetch driver data from the database
        List<Driver> drivers = adminService.getAllDrivers();

        // Pass driver data to the view
        model.addAttribute("drivers", drivers);

        // Return the view name for the admin dashboard
        return "admin_dashboard";
    }
    private int generateLoginId() {
        // Generate a random number using current time as seed
        Random random = new Random(System.currentTimeMillis());
        // Convert the random number to a string and return it
        return Integer.parseInt(String.valueOf(random.nextInt(10000)));
    }

    @PostMapping("/toggleVehicles")
    public String toggleVehicles(Model model){
        boolean vehicleVisible = !(boolean) model.getAttribute("vehicleVisible");
        model.addAttribute("vehicleVisible", vehicleVisible);

        // Fetch driver data from the database
        List<Vehicle> vehicles = adminService.getAllVehicles();

        // Pass driver data to the view
        model.addAttribute("vehicles", vehicles);

        // Return the view name for the admin dashboard
        return "admin_dashboard";
    }

    @PostMapping("/toggleCustomers")
    public String toggleCustomers(Model model) {
        // Toggle the visibility flag for drivers table
        boolean customersVisible = !(boolean) model.getAttribute("customersVisible");
        model.addAttribute("customersVisible", customersVisible);

        // Fetch driver data from the database
        List<Customer> customers = adminService.getAllCustomers();

        // Pass driver data to the view
        model.addAttribute("customers", customers);

        // Return the view name for the admin dashboard
        return "admin_dashboard";
    }



    // Method to generate a random PIN (for demonstration purposes)
    private String generatePin() {
        Random random = new Random();
        return String.valueOf(random.nextInt(10000));
    }

    public void addCustomers(String name, String phone, String address, String email) {
        int loginId = generateLoginId();
        String pin = generatePin();

        // Save login information
        Login login = new Login();
        login.setLoginId(loginId);
        login.setRole("customer");
        login.setPin(Integer.parseInt(pin));
        loginService.save(login);

        // Save customer information
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhone(Integer.parseInt(phone));
        customer.setAddress(address);
        customer.setEmail(email);
        customer.setLogin(login); // Set the generated login ID
        customerService.save(customer);
    }

    @PostMapping("/addDriver")
    public String addDriver(@RequestParam String driverName, @RequestParam String driverPhone,
                            @RequestParam String driverAddress, @RequestParam String driverEmail,
                            @RequestParam Integer loginId, Model model) {
        // Logic to add driver to the database

        // Redirect to the admin dashboard with the loginId
        return "redirect:/admin/{loginId}";
    }
    @PostMapping("/addCustomer")
    public String addCustomer(@RequestParam String customerName, @RequestParam String customerPhone,
                              @RequestParam String customerAddress, @RequestParam String customerEmail,
                              @RequestParam int loginId, Model model) {
        // Generate a random PIN for the customer login
        int generatedLoginId = generateLoginId();
        String pin = generatePin();

        // Save login information
        Login login = new Login();
        login.setLoginId(generatedLoginId);
        login.setRole("customer");
        login.setPin(Integer.parseInt(pin));
        loginService.save(login);

        // Save customer information
        Customer customer = new Customer();
        customer.setName(customerName);
        customer.setPhone(Integer.parseInt(customerPhone));
        customer.setAddress(customerAddress);
        customer.setEmail(customerEmail);
        customer.setLogin(login);
        customerService.save(customer);

        // Redirect to the admin dashboard with the generatedLoginId
        return "redirect:/admin/{loginId}";
    }


    // Method to generate a random PIN (for demonstration purposes)



}
