package packages.project.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import packages.project.Driver.Driver;
import packages.project.Driver.DriverService;
import packages.project.Login.Login;
import packages.project.Login.LoginService;
import packages.project.Schedule.Schedule;
import packages.project.Vehicle.Vehicle;
import packages.project.Vehicle.VehicleService;

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class CustomerController {

    private final CustomerService customerService;
    private final DriverService driverService;
    private final LoginService loginService;

    @Autowired
    public CustomerController(CustomerService customerService , DriverService driverService,LoginService loginService) {
        this.customerService = customerService;
        this.driverService=driverService;
        this.loginService=loginService;
    }


    @GetMapping("/customers/{loginId}")
    public String showCustomerDashboard(@PathVariable Integer loginId, Model model) {
        Customer customer = customerService.getCustomer(loginId);

        if (customer != null) {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            model.addAttribute("customer", customer);


            Vehicle vehicle = customerService.getVehicle(loginId);
            Driver driver = driverService.getDriverByVehicleId(vehicle.getVehicleId());
            model.addAttribute("vehicle", vehicle);
            model.addAttribute("driver", driver);
            return "customer_dashboard";
        } else {
            return "customer_not_found"; // Render error page if customer is not found
        }
    }

    @GetMapping("/customers/{loginId}/edit")
    public String showEditForm(@PathVariable Integer loginId , Model model) {
        // Add logic to retrieve customer information and pass it to the template
        Customer customer = customerService.getCustomer(loginId);
        model.addAttribute("customer", customer);
        return "customer_edit"; // Name of the edit form template
    }

    @PostMapping("/customers/{loginId}/edit")
    public String editCustomerInfo(@PathVariable int loginId,
                                   @RequestParam String name,
                                   @RequestParam String phone,
                                   @RequestParam String email,
                                   @RequestParam String address,
                                   @RequestParam int pin,
                                   Model model) {

        // Retrieve the customer and login objects
        Customer customer = customerService.getCustomer(loginId);
        Login login = customer.getLogin();

        // Update customer information
        customer.setName(name);
        customer.setPhone(Integer.parseInt(phone));
        customer.setEmail(email);
        customer.setAddress(address);

        // Update login PIN
        login.setPin(pin);
        loginService.save(login);

        // Save updated customer
        customerService.save(customer);

        // Redirect to the customer details page
        return "redirect:/customers/" + loginId;
    }

    @GetMapping("/api/customers/{loginId}")
    public String redirectToCustomerDashboard(@PathVariable Long loginId) {
        // Construct the redirect URL
        String redirectUrl = "/customers/" + loginId;

        // Redirect to the specified URL
        return "redirect:" + redirectUrl;
    }




}
