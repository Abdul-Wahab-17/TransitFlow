package packages.project.SuperAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import packages.project.Admin.Admin;
import packages.project.Admin.AdminRepository;
import packages.project.Admin.AdminService;
import packages.project.Login.Login;
import packages.project.Login.LoginService;

import java.util.Map;

@Controller
public class SuperAdminController {

    @Autowired
    AdminService adminService;
@Autowired
    LoginService loginService;

@Autowired
    AdminRepository adminRepository;


    @GetMapping("/api/superadmin/6969")
    public String getSuperAdminPage(){

        return "superAdmin";
    }

    @PostMapping("/api/superadmin/6969")
    public ResponseEntity<?> insertAdmin(@RequestParam String password , @RequestParam String name, @RequestParam int phone , @RequestParam String email , @RequestParam String address) {
        try {
            Login login = new Login();
            login.setPin(Integer.parseInt(password));
            login.setRole("admin");
            loginService.save(login);

            Admin admin = new Admin();
            admin.setName(name);
            admin.setPhone(phone);
            admin.setEmail(email);
            admin.setAddress(address);
            admin.setLogin(login);

            adminRepository.save(admin);

            return ResponseEntity.ok().body(new ApiResponse(true, "Admin inserted successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error inserting admin: " + e.getMessage()));
        }
    }


    public class ApiResponse {
        private boolean success;
        private String message;

        public ApiResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        // Getters and Setters
    }

}
