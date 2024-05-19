package packages.project.Driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import packages.project.Customer.Customer;
import packages.project.Customer.CustomerService;
import packages.project.Login.Login;
import packages.project.Login.LoginService;

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class DriverController {

    private final DriverService driverService;
    private final CustomerService customerService;
    private final LoginService loginService;

    @Autowired
    public DriverController(DriverService driverService, CustomerService customerService,LoginService loginService) {
        this.driverService = driverService;
        this.customerService=customerService;
        this.loginService=loginService;
    }

    @GetMapping("/drivers/{loginId}")
    public String showDriverDashboard(@PathVariable Integer loginId, Model model) {
        Driver driver = driverService.getDriver(loginId);

        if (driver != null) {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            List<Customer> customers = customerService.getCustomersForDriver(driverService.findDriverId(loginId));

            // Check if customers is null or empty
            if (customers != null && !customers.isEmpty()) {
                model.addAttribute("driver", driver);
                model.addAttribute("customers", customers);
                return "driver_dashboard"; // View name without file extension
            } else {
                // Handle case when no customers are found
                model.addAttribute("driver", driver);
                return "driver_dashboard"; // Render a specific view for this case
            }
        } else {
            return "driver_not_found"; // Render error page if driver is not found
        }
    }

    @GetMapping("/api/drivers/{loginId}")
    public String redirectToDriverDashboard(@PathVariable Long loginId) {
        // Construct the redirect URL
        String redirectUrl = "/drivers/" + loginId;

        // Redirect to the specified URL
        return "redirect:" + redirectUrl;
    }
    @GetMapping("/drivers/{loginId}/edit")
    public String showEditForm(@PathVariable Integer loginId , Model model) {
        // Add logic to retrieve customer information and pass it to the template
        Driver driver = driverService.getDriver(loginId);
        model.addAttribute("driver", driver);
        return "driver_edit"; // Name of the edit form template
    }

    @PostMapping("/drivers/{loginId}/edit")
    public String editDriverInfo(@PathVariable int loginId,
                                 @RequestParam String name,
                                 @RequestParam String phone,
                                 @RequestParam String email,
                                 @RequestParam String address,
                                 @RequestParam int pin,
                                 Model model) {

        // Retrieve the driver and login objects
        Driver driver = driverService.getDriver(loginId);
        Login login = driver.getLogin();

        // Update driver information
        driver.setName(name);
        driver.setPhone(Integer.parseInt(phone));
        driver.setEmail(email);
        driver.setAddress(address);

        // Update login PIN
        login.setPin(pin);
        loginService.save(login);

        // Save updated driver
        driverService.save(driver);

        // Redirect to the driver details page
        return "redirect:/drivers/" + loginId;
    }
}
