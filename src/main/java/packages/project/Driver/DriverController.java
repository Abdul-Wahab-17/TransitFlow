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

            if (customers != null && !customers.isEmpty()) {
                model.addAttribute("driver", driver);
                model.addAttribute("customers", customers);
                return "driver_dashboard";
            } else {
                model.addAttribute("driver", driver);
                return "driver_dashboard";
            }
        } else {
            return "driver_not_found";
        }
    }

    @GetMapping("/api/drivers/{loginId}")
    public String redirectToDriverDashboard(@PathVariable Long loginId) {

        String redirectUrl = "/drivers/" + loginId;

        return "redirect:" + redirectUrl;
    }
    @GetMapping("/drivers/{loginId}/edit")
    public String showEditForm(@PathVariable Integer loginId , Model model) {
        Driver driver = driverService.getDriver(loginId);
        model.addAttribute("driver", driver);
        return "driver_edit";
    }

    @PostMapping("/drivers/{loginId}/edit")
    public String editDriverInfo(@PathVariable int loginId,
                                 @RequestParam String name,
                                 @RequestParam String phone,
                                 @RequestParam String email,
                                 @RequestParam String address,
                                 @RequestParam int pin,
                                 Model model) {

        Driver driver = driverService.getDriver(loginId);
        Login login = driver.getLogin();

        driver.setName(name);
        driver.setPhone(Integer.parseInt(phone));
        driver.setEmail(email);
        driver.setAddress(address);

        login.setPin(pin);
        loginService.save(login);

        driverService.save(driver);

        return "redirect:/drivers/" + loginId;
    }
}
