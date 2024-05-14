package packages.project.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/")
    public String getMainPage() {
        return "main";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/api/login/authenticate")
    public String authenticate(@RequestParam("loginId") Integer loginId,
                               @RequestParam("password") String password,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        Login user = loginService.authenticateUser(loginId, Integer.parseInt(password));
        String redirectUrl = "error"; // Default redirect URL in case of failure

        if (user != null) {
            String role = user.getRole();

            switch (role) {
                case "driver":
                    redirectUrl = "/api/drivers/" + loginId;
                    break;
                case "customer":
                    redirectUrl = "/api/customers/" + loginId;
                    break;
                case "admin":
                    redirectUrl = "/api/admin/" + loginId;
                    break;
                default:
                    redirectAttributes.addFlashAttribute("error", "Unknown user role");
                    return "redirect:/login"; // Redirect to the login page with an error message
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Authentication failed");
        }

        return "redirect:" + redirectUrl;
    }
}
