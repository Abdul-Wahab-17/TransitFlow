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
import packages.project.Security.AuthenticationService;
import packages.project.Security.JwtTokenUtil;

@Controller
public class LoginController {

    private final LoginService loginService;
    private final AuthenticationService authenticationService;

    @Autowired
    public LoginController(LoginService loginService,AuthenticationService authenticationService) {
        this.loginService = loginService;
        this.authenticationService=authenticationService;
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
    public ResponseEntity<?> authenticate(@RequestParam("loginId") Integer loginId,
                                          @RequestParam("password") int password) {
        // Authenticate the user
        Login user = loginService.authenticateUser(loginId, password);

        if (user != null) {
            String role = user.getRole();
            String token =authenticationService.authenticateUser( String.format(""+loginId) , String.format(""+password));// Generate JWT token here using your token generation logic

            // Return the JWT token in the response headers
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(user); // Return the authenticated user or any relevant data
        } else {
            // If authentication fails, return unauthorized status
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
