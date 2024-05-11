package packages.project.Login;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import packages.project.Security.AuthenticationService;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final LoginService loginService;
    private final AuthenticationService authenticationService;

    @Autowired
    public LoginController(LoginService loginService, AuthenticationService authenticationService) {
        this.loginService = loginService;
        this.authenticationService = authenticationService;
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
                               @RequestParam("password") Integer password,
                               Model model,
                               HttpSession session) {
        try {
            // Authenticate user
            Subject currentUser = authenticationService.login(String.valueOf(loginId), String.valueOf(password));
            if (currentUser.isAuthenticated()) {
                // Store user ID in session
                session.setAttribute("loggedInUserId", loginId);

                // Redirect to appropriate URL based on role
                String role = loginService.getRole(loginId).toLowerCase();
                String redirectUrl = "/api/" + role + "/" + loginId;
                return "redirect:" + redirectUrl;
            } else {
                model.addAttribute("error", "Invalid login credentials");
                return "login";
            }
        } catch (AuthenticationException e) {
            model.addAttribute("error", "Invalid login credentials");
            return "login";
        }
    }
}
