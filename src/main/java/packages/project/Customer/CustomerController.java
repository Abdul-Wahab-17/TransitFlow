package packages.project.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import packages.project.Driver.Driver;
import packages.project.Driver.DriverService;
import packages.project.Login.Login;
import packages.project.Login.LoginService;
import packages.project.Security.AuthSession;
import packages.project.Vehicle.Vehicle;

import javax.servlet.http.HttpSession;

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


    @GetMapping("/customers/me")
    public String showMyCustomerDashboard(HttpSession session, Model model) {
        Integer loginId = AuthSession.loginId(session);
        if (!AuthSession.isRole(session, "customer") || loginId == null) {
            return "redirect:/login?error";
        }
        return showCustomerDashboard(loginId, session, model);
    }

    @GetMapping("/customers/{loginId}")
    public String showCustomerDashboard(@PathVariable Integer loginId, HttpSession session, Model model) {
        if (!canViewCustomer(session, loginId)) {
            return "redirect:/login?error";
        }

        Customer customer = customerService.getCustomer(loginId);

        if (customer != null) {
            model.addAttribute("customer", customer);


            Vehicle vehicle = customerService.getVehicle(loginId);
            Driver driver = vehicle == null ? null : driverService.getDriverByVehicleId(vehicle.getVehicleId());
            model.addAttribute("vehicle", vehicle);
            model.addAttribute("driver", driver);
            return "customer_dashboard";
        } else {
            return "customer_not_found";
        }
    }

    @GetMapping("/customers/me/edit")
    public String showMyEditForm(HttpSession session, Model model) {
        Integer loginId = AuthSession.loginId(session);
        if (!AuthSession.isRole(session, "customer") || loginId == null) {
            return "redirect:/login?error";
        }
        return showEditForm(loginId, session, model);
    }

    @GetMapping("/customers/{loginId}/edit")
    public String showEditForm(@PathVariable Integer loginId, HttpSession session, Model model) {
        if (!canEditCustomer(session, loginId)) {
            return "redirect:/login?error";
        }

        Customer customer = customerService.getCustomer(loginId);
        if (customer == null) {
            return "redirect:/login?error";
        }
        model.addAttribute("customer", customer);
        return "customer_edit";
    }

    @PostMapping("/customers/me/edit")
    public String editMyCustomerInfo(@RequestParam String name,
                                     @RequestParam String phone,
                                     @RequestParam String email,
                                     @RequestParam String address,
                                     @RequestParam int pin,
                                     HttpSession session,
                                     Model model) {
        Integer loginId = AuthSession.loginId(session);
        if (!AuthSession.isRole(session, "customer") || loginId == null) {
            return "redirect:/login?error";
        }
        return editCustomerInfo(loginId, name, phone, email, address, pin, session, model);
    }

    @PostMapping("/customers/{loginId}/edit")
    public String editCustomerInfo(@PathVariable int loginId,
                                   @RequestParam String name,
                                   @RequestParam String phone,
                                   @RequestParam String email,
                                   @RequestParam String address,
                                   @RequestParam int pin,
                                   HttpSession session,
                                   Model model) {
        if (!canEditCustomer(session, loginId)) {
            return "redirect:/login?error";
        }

        Customer customer = customerService.getCustomer(loginId);
        if (customer == null) {
            return "redirect:/login?error";
        }
        Login login = customer.getLogin();

        customer.setName(name);
        customer.setPhone(Integer.parseInt(phone));
        customer.setEmail(email);
        customer.setAddress(address);

        login.setPin(pin);
        loginService.save(login);

        customerService.save(customer);

        if (AuthSession.isAdmin(session)) {
            return "redirect:/customers/" + loginId;
        }
        return "redirect:/customers/me";
    }

    @GetMapping("/api/customers/{loginId}")
    public String redirectToCustomerDashboard(@PathVariable Integer loginId, HttpSession session) {
        if (!canViewCustomer(session, loginId)) {
            return "redirect:/login?error";
        }

        String redirectUrl = AuthSession.isAdmin(session) ? "/customers/" + loginId : "/customers/me";

        return "redirect:" + redirectUrl;
    }

    private boolean canViewCustomer(HttpSession session, Integer loginId) {
        return AuthSession.isAdmin(session) || AuthSession.canAccessOwnRole(session, "customer", loginId);
    }

    private boolean canEditCustomer(HttpSession session, Integer loginId) {
        return canViewCustomer(session, loginId);
    }
}
