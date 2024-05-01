package packages.project.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import packages.project.Customer.Customer;
import packages.project.Driver.Driver;

import java.util.List;

@Controller
public class AdminController {

    AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService){
        this.adminService=adminService;
    }
    @GetMapping("/api/admin/{loginId}")
    public String redirectToAdminDashboard(@PathVariable Long loginId) {
        // Construct the redirect URL
        String redirectUrl = "/admin/" + loginId;

        // Redirect to the specified URL
        return "redirect:" + redirectUrl;
    }
    @GetMapping("/admin/{loginId}")
    public String getAdminDashboard(@PathVariable Integer loginId, Model model) {
        Admin admin = adminService.getAdmin(loginId);
        model.addAttribute("admin", admin);

        // Fetch driver data from the database
        List<Driver> drivers = adminService.getAllDrivers();
        model.addAttribute("drivers", drivers);

        List<Customer> customers = adminService.getAllCustomers();
        model.addAttribute("customers" , customers);

        // Initialize driversVisible attribute to false initially
        model.addAttribute("driversVisible", false);
        model.addAttribute("customersVisible" , false);

        return "admin_dashboard";
    }


    @PostMapping("/toggleDrivers")
    public String toggleDrivers(Model model) {
        // Toggle the visibility flag for drivers table
        boolean driversVisible = !(boolean) model.getAttribute("driversVisible");
        model.addAttribute("driversVisible", driversVisible);

        // Fetch driver data from the database
        List<Driver> drivers = adminService.getAllDrivers();

        // Pass driver data to the view
        model.addAttribute("drivers", drivers);

        // Return the view name for the admin dashboard
        return "admin_dashboard";
    }

    @PostMapping("/toggleCustomers")
    public String toggleCustomers(Model model) {
        // Toggle the visibility flag for drivers table
        boolean customersVisible = !(boolean) model.getAttribute("customersVisible");
        model.addAttribute("customersVisible", customersVisible);

        // Fetch driver data from the database
        List<Customer> customers = adminService.getAllCustomers();

        // Pass driver data to the view
        model.addAttribute("customers", customers);

        // Return the view name for the admin dashboard
        return "admin_dashboard";
    }


}
