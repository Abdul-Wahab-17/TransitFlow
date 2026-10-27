package packages.project.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import packages.project.Security.AuthSession;
import packages.project.Security.SuperAdminCredentials;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    private final LoginService loginService;
    private final SuperAdminCredentials superAdmin;

    @Autowired
    public LoginController(LoginService loginService, SuperAdminCredentials superAdmin) {
        this.loginService = loginService;
        this.superAdmin = superAdmin;
    }

    @GetMapping("/")
    public String getHomePage() {
        return "main";
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid login ID or PIN.");
        }
        return "login";
    }

    @GetMapping("/about")
    public String getAboutPage(){
        return "about";
    }
    @GetMapping("/contact")
    public String getContactPage(){
        return "contact";
    }

    @PostMapping("/api/login/authenticate")
    public String authenticate(@RequestParam("loginId") Integer loginId,
                               @RequestParam("password") String password,
                               Model model,
                               HttpServletRequest request) {
        Integer pin = parsePin(password);
        if (loginId == null || pin == null) {
            return "redirect:/login?error";
        }

        Login user = loginService.authenticateUser(loginId, pin);

        if (user != null) {
            String role = AuthSession.normalizeRole(user.getRole());
            if (!role.equals("driver") && !role.equals("customer") && !role.equals("admin")) {
                return "redirect:/login?error";
            }

            AuthSession.signIn(request, loginId, role);
            return "redirect:/dashboard";
        }

        if (superAdmin.matches(loginId, pin)) {
            AuthSession.signIn(request, loginId, "superadmin");
            return "redirect:/superadmin";
        }

        return "redirect:/login?error";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        if (AuthSession.isRole(session, "driver")) {
            return "redirect:/drivers/me";
        }
        if (AuthSession.isRole(session, "customer")) {
            return "redirect:/customers/me";
        }
        if (AuthSession.isRole(session, "admin")) {
            return "redirect:/admin/dashboard";
        }
        if (AuthSession.isSuperAdmin(session)) {
            return "redirect:/superadmin";
        }
        return "redirect:/login?error";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        AuthSession.signOut(session);
        return "redirect:/login";
    }

    private Integer parsePin(String password) {
        try {
            return Integer.parseInt(password);
        } catch (NumberFormatException e) {
            return null;
        }
    }


  /*  @PostMapping("/api/login/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }*/

/*
  @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
  }*/

//     @PostMapping("/login/authenticate")
//     public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){
//         return ResponseEntity.ok(service.authenticate(request));
//     }
}
