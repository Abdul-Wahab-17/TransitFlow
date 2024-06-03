package packages.project.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import packages.project.Admin.Admin;
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
    public CustomerController(CustomerService customerService, DriverService driverService, LoginService loginService) {
        this.customerService = customerService;
        this.driverService = driverService;
        this.loginService = loginService;
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
            return "customer_not_found";
        }
    }

    @GetMapping("/customers/{loginId}/edit")
    public String showEditForm(@PathVariable Integer loginId, Model model) {
        Customer customer = customerService.getCustomer(loginId);
        model.addAttribute("customer", customer);
        return "customer_edit";
    }

    @PostMapping("/customers/{loginId}/edit")
    public String editCustomerInfo(@PathVariable int loginId,
                                   @RequestParam String name,
                                   @RequestParam String phone,
                                   @RequestParam String email,
                                   @RequestParam String address,
                                   @RequestParam int pin,
                                   Model model) {

        Customer customer = customerService.getCustomer(loginId);
        Login login = customer.getLogin();

        customer.setName(name);
        customer.setPhone(Integer.parseInt(phone));
        customer.setEmail(email);
        customer.setAddress(address);

        login.setPin(pin);
        loginService.save(login);

        customerService.save(customer);

        return "redirect:/customers/" + loginId;
    }

    @GetMapping("/api/customers/{loginId}")
    public String redirectToCustomerDashboard(@PathVariable Long loginId) {

        String redirectUrl = "/customers/" + loginId;

        return "redirect:" + redirectUrl;
    }
}
