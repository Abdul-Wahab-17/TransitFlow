package packages.project.Schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import packages.project.Customer.Customer;
import packages.project.Customer.CustomerService;
import packages.project.Driver.Driver;
import packages.project.Driver.DriverService;
import packages.project.Security.AuthSession;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final CustomerService customerService;
    private final DriverService driverService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService , CustomerService customerService , DriverService driverService) {
        this.scheduleService = scheduleService;
        this.customerService=customerService;
        this.driverService=driverService;
    }

    @PostMapping("/updateSchedule")
    public String updateSchedule(@RequestParam int customerId,
                                 @RequestParam(required = false) Boolean mondayMorning,
                                 @RequestParam(required = false) Boolean mondayEvening,
                                 @RequestParam(required = false) Boolean tuesdayMorning,
                                 @RequestParam(required = false) Boolean tuesdayEvening,
                                 @RequestParam(required = false) Boolean wednesdayMorning,
                                 @RequestParam(required = false) Boolean wednesdayEvening,
                                 @RequestParam(required = false) Boolean thursdayMorning,
                                 @RequestParam(required = false) Boolean thursdayEvening,
                                 @RequestParam(required = false) Boolean fridayMorning,
                                 @RequestParam(required = false) Boolean fridayEvening,
                                 HttpSession session) {
        Schedule schedule = scheduleService.getCustomerSchedule(customerId);
        if (schedule == null) {
            return "redirect:/login?error";
        }
        int loginId = customerService.getloginId(customerId);
        if (!AuthSession.isAdmin(session) && !AuthSession.canAccessOwnRole(session, "customer", loginId)) {
            return "redirect:/login?error";
        }

        schedule.setMondayMorning(mondayMorning != null);
        schedule.setMondayEvening(mondayEvening != null);
        schedule.setTuesdayMorning(tuesdayMorning != null);
        schedule.setTuesdayEvening(tuesdayEvening != null);
        schedule.setWednesdayMorning(wednesdayMorning != null);
        schedule.setWednesdayEvening(wednesdayEvening != null);
        schedule.setThursdayMorning(thursdayMorning != null);
        schedule.setThursdayEvening(thursdayEvening != null);
        schedule.setFridayMorning(fridayMorning != null);
        schedule.setFridayEvening(fridayEvening != null);
        scheduleService.save(schedule);
        return AuthSession.isAdmin(session)
                ? "redirect:/customers/" + loginId + "/schedule"
                : "redirect:/customers/me/schedule";
    }


    @GetMapping("/customers/me/schedule")
    public String getMySchedulePage(HttpSession session, Model model) {
        Integer loginId = AuthSession.loginId(session);
        if (!AuthSession.isRole(session, "customer") || loginId == null) {
            return "redirect:/login?error";
        }
        return getSchedulePage(String.valueOf(loginId), session, model);
    }

    @GetMapping("/customers/{loginId}/schedule")
    public String getSchedulePage(@PathVariable String loginId, HttpSession session, Model model){
        Integer parsedLoginId = parseInteger(loginId);
        if (parsedLoginId == null) {
            return "redirect:/login?error";
        }
        if (!AuthSession.isAdmin(session) && !AuthSession.canAccessOwnRole(session, "customer", parsedLoginId)) {
            return "redirect:/login?error";
        }

        Customer customer = customerService.getCustomer(parsedLoginId);
        if (customer == null) {
            return "redirect:/login?error";
        }
        int customerId = customer.getCustomerId();
        Schedule schedule = scheduleService.getCustomerSchedule(customerId);
        if (schedule == null) {
            return "redirect:/customers/" + loginId;
        }
        model.addAttribute("schedule",schedule);
        return "customerSchedule";
    }

    @GetMapping("/drivers/me/schedule")
    public String viewMyCustomersSchedule(HttpSession session, Model model) {
        Integer loginId = AuthSession.loginId(session);
        if (!AuthSession.isRole(session, "driver") || loginId == null) {
            return "redirect:/login?error";
        }
        return viewCustomersSchedule(loginId, session, model);
    }

    @GetMapping("/drivers/{loginId}/schedule")
    public String viewCustomersSchedule(@PathVariable int loginId, HttpSession session, Model model) {
        if (!AuthSession.isAdmin(session) && !AuthSession.canAccessOwnRole(session, "driver", loginId)) {
            return "redirect:/login?error";
        }

        Driver driver = driverService.getDriver(loginId);

        if (driver == null) {
            return "error-page";
        }

        List<Customer> customers = customerService.getCustomersForDriver(driver.getDriverId());

        model.addAttribute("driver", driver);
        model.addAttribute("customers", customers);

        return "driver_customer";
    }

    private Integer parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
