package packages.project.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // This corresponds to the login.html template
    }

    @PostMapping("/api/login/authenticate")
    public String authenticate(@RequestParam("loginId") Integer loginId,
                               @RequestParam("password") String password,
                               Model model) {
        Login user = loginService.authenticateUser(loginId, Integer.parseInt(password));

        if (user != null) {
            String role = user.getRole();
            String redirectUrl = "/";

            switch (role) {
                case "driver":
                    redirectUrl = "/api/driver/" + loginId;
                    break;
                case "customer":
                    redirectUrl = "/api/customers/" + loginId;
                    break;
                case "admin":
                    redirectUrl = "/api/admin/" + loginId;
                    break;
                default:
                    return "error"; // Handle unknown role
            }

            return "redirect:" + redirectUrl;
        } else {
            model.addAttribute("error", "Invalid login credentials");
            return "login"; // Redirect back to login page with error message
        }

    }
}
