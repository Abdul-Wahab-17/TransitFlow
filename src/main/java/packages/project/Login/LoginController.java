package packages.project.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;

@Controller
public class LoginController {

    private final LoginService loginService;
   // private final AuthenticationService authenticationService;

    @Autowired
    public LoginController(LoginService loginService/*,AuthenticationService authenticationService*/) {
        this.loginService = loginService;
      //  this.authenticationService=authenticationService;
    }

    @GetMapping("/")
    public String getHomePage() {
        return "main";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "Login";
    }

    @GetMapping("/about")
    public String getAboutPage(){
        return "about";
    }
    @GetMapping("/contact")
    public String getContactPage(){
        return "contact";
    }

   /* @PostMapping("/api/login/authenticate")
    public ResponseEntity<?> authenticate(@RequestParam("loginId") Integer loginId,
                                          @RequestParam("password") int password,
                                          RedirectAttributes redirectAttributes) {
        // Authenticate the user
        Login user = loginService.authenticateUser(loginId, password);

        if (user != null) {
            String role = user.getRole();
            String token = authenticationService.authenticateUser(String.valueOf(loginId), String.format("" + password));
            // Generate JWT token here using your token generation logic
            System.out.println(token);
            // Return the JWT token in the response headers
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

            // Add user information to the redirect attributes
            redirectAttributes.addFlashAttribute("user", user);

            // Redirect to a different endpoint along with the JWT token
            String redirectUrl = "/" + role + "/" + loginId;

            return ResponseEntity.status(HttpStatus.FOUND)
                    .headers(headers)
                    .location(URI.create(redirectUrl)) // Redirect to the success endpoint
                    .build();
        } else {
            // If authentication fails, return unauthorized status
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
    }
*/


    @PostMapping("/api/login/authenticate")
    public String authenticate(@RequestParam("loginId") Integer loginId,
                               @RequestParam("password") String password,
                               Model model) {
        Login user = loginService.authenticateUser(loginId, Integer.parseInt(password));

        if (user != null) {
            String role = user.getRole();
            String redirectUrl;

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
                    return "error"; // Handle unknown role
            }
            return "redirect:" + redirectUrl;
        } else {
            model.addAttribute("error", "Invalid login credentials");
            return "Login"; // Redirect back to login page with error message
        }
    }
}
