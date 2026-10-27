package packages.project.Driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import packages.project.Customer.Customer;
import packages.project.Customer.CustomerService;
import packages.project.Login.Login;
import packages.project.Login.LoginService;
import packages.project.Security.AuthSession;

import javax.servlet.http.HttpSession;
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

    @GetMapping("/drivers/me")
    public String showMyDriverDashboard(HttpSession session, Model model) {
        Integer loginId = AuthSession.loginId(session);
        if (!AuthSession.isRole(session, "driver") || loginId == null) {
            return "redirect:/login?error";
        }
        return showDriverDashboard(loginId, session, model);
    }

    @GetMapping("/drivers/{loginId}")
    public String showDriverDashboard(@PathVariable Integer loginId, HttpSession session, Model model) {
        if (!canViewDriver(session, loginId)) {
            return "redirect:/login?error";
        }

        Driver driver = driverService.getDriver(loginId);

        if (driver != null) {
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
    public String redirectToDriverDashboard(@PathVariable Integer loginId, HttpSession session) {
        if (!canViewDriver(session, loginId)) {
            return "redirect:/login?error";
        }

        String redirectUrl = AuthSession.isAdmin(session) ? "/drivers/" + loginId : "/drivers/me";

        return "redirect:" + redirectUrl;
    }

    @GetMapping("/drivers/me/edit")
    public String showMyEditForm(HttpSession session, Model model) {
        Integer loginId = AuthSession.loginId(session);
        if (!AuthSession.isRole(session, "driver") || loginId == null) {
            return "redirect:/login?error";
        }
        return showEditForm(loginId, session, model);
    }

    @GetMapping("/drivers/{loginId}/edit")
    public String showEditForm(@PathVariable Integer loginId, HttpSession session, Model model) {
        if (!canEditDriver(session, loginId)) {
            return "redirect:/login?error";
        }

        Driver driver = driverService.getDriver(loginId);
        if (driver == null) {
            return "redirect:/login?error";
        }
        model.addAttribute("driver", driver);
        return "driver_edit";
    }

    @PostMapping("/drivers/me/edit")
    public String editMyDriverInfo(@RequestParam String name,
                                   @RequestParam String phone,
                                   @RequestParam String email,
                                   @RequestParam String address,
                                   @RequestParam int pin,
                                   HttpSession session,
                                   Model model) {
        Integer loginId = AuthSession.loginId(session);
        if (!AuthSession.isRole(session, "driver") || loginId == null) {
            return "redirect:/login?error";
        }
        return editDriverInfo(loginId, name, phone, email, address, pin, session, model);
    }

    @PostMapping("/drivers/{loginId}/edit")
    public String editDriverInfo(@PathVariable int loginId,
                                 @RequestParam String name,
                                 @RequestParam String phone,
                                 @RequestParam String email,
                                 @RequestParam String address,
                                 @RequestParam int pin,
                                 HttpSession session,
                                 Model model) {
        if (!canEditDriver(session, loginId)) {
            return "redirect:/login?error";
        }

        Driver driver = driverService.getDriver(loginId);
        if (driver == null) {
            return "redirect:/login?error";
        }
        Login login = driver.getLogin();

        driver.setName(name);
        driver.setPhone(Integer.parseInt(phone));
        driver.setEmail(email);
        driver.setAddress(address);

        loginService.setPin(login, pin);
        loginService.save(login);

        driverService.save(driver);

        if (AuthSession.isAdmin(session)) {
            return "redirect:/drivers/" + loginId;
        }
        return "redirect:/drivers/me";
    }

    private boolean canViewDriver(HttpSession session, Integer loginId) {
        return AuthSession.isAdmin(session) || AuthSession.canAccessOwnRole(session, "driver", loginId);
    }

    private boolean canEditDriver(HttpSession session, Integer loginId) {
        return canViewDriver(session, loginId);
    }
}
