package packages.project.Driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import packages.project.Customer.Customer;

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class DriverController {

    private final DriverRepository driverRepository;

    @Autowired
    public DriverController(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }


    @GetMapping("/drivers/{loginId}")
    public String showDriverDashboard(@PathVariable Integer loginId, Model model) {
        Driver driver = driverRepository.getDriverByDriverId(driverRepository.getDriverIdbyLoginId(loginId));

        if (driver != null) {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            model.addAttribute("driver", driver);

            return "driver_dashboard"; // View name without file extension
        } else {
            return "driver_not_found"; // Render error page if customer is not found
        }
    }




    @GetMapping("/api/drivers/{loginId}")
    public String redirectToDriverDashboard(@PathVariable Long loginId) {
        // Construct the redirect URL
        String redirectUrl = "/drivers/" + loginId;

        // Redirect to the specified URL
        return "redirect:" + redirectUrl;
    }


}
