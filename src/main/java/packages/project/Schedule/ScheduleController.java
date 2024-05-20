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
                                 @RequestParam(required = false) Boolean fridayEvening) {
        Schedule schedule = scheduleService.getCustomerSchedule(customerId);
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
        int loginId = customerService.getloginId(customerId);
        return "redirect:/customers/" +loginId +"/schedule";
    }



    @GetMapping("/customers/{loginId}/schedule")
    public String getSchedulePage(@PathVariable String loginId , Model model){

        Customer customer = customerService.getCustomer(Integer.parseInt(loginId));
        int customerId = customer.getCustomerId();
        Schedule schedule = scheduleService.getCustomerSchedule(customerId);
        model.addAttribute("schedule",schedule);
        return "customerSchedule";
    }

    @GetMapping("/drivers/{loginId}/schedule")
    public String viewCustomersSchedule(@PathVariable int loginId, Model model) {
        // Get the driver based on loginId
        Driver driver = driverService.getDriver(loginId);

        if (driver == null) {
            // Handle case where driver with given loginId is not found
            return "error-page"; // Return an error page or appropriate response
        }

        // Get customers associated with the driver
        List<Customer> customers = customerService.getCustomersForDriver(driver.getDriverId());

        // Add driver and customers to the model
        model.addAttribute("driver", driver);
        model.addAttribute("customers", customers);

        // Return the name of the view
        return "driver_customer"; // Adjust this to your actual view name
    }

}
