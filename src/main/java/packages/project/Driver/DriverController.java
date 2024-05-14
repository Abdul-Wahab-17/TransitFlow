package packages.project.Driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import packages.project.Customer.Customer;
import packages.project.Customer.CustomerService;

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class DriverController {

    private final DriverService driverService;
    private final CustomerService customerService;

    @Autowired
    public DriverController(DriverService driverService, CustomerService customerService) {
        this.driverService = driverService;
        this.customerService=customerService;
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
    public String editCustomer(@PathVariable int loginId, @ModelAttribute Driver updatedDriver) {
        Driver existingDriver = driverService.getDriver(loginId);
        if (existingDriver != null) {
            // Update the existing customer entity with the new information
            existingDriver.setName(updatedDriver.getName());
            existingDriver.setPhone(updatedDriver.getPhone());
            existingDriver.setEmail(updatedDriver.getEmail());
            existingDriver.setAddress(updatedDriver.getAddress());
            // Similarly, update other fields as needed

            // Save the updated customer entity in the database
            driverService.save(existingDriver);
        }
        // Redirect to the customer dashboard page after editing
        return "redirect:/drivers/" + loginId;
    }

}
