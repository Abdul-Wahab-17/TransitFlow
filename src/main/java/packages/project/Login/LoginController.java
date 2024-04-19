package packages.project.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                               @RequestParam("password") int password,
                               Model model) {

        // Perform authentication using the LoginService
        boolean isAuthenticated = loginService.authenticate(loginId, password);

        if (isAuthenticated) {
            // Successful login logic (e.g., set user session, redirect to dashboard)
            return "redirect:/dashboard";
        } else {
            // Failed login logic (e.g., display error message)
            model.addAttribute("error", "Invalid login credentials");
            return "login";
        }
    }

    @GetMapping("dashboard")
    public String showDashBoard() {
        return "dashboard"; // This corresponds to the login.html template
    }



}
