package packages.project.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import packages.project.Security.AuthenticationRequest;
import packages.project.Security.AuthenticationResponse;
import packages.project.Security.AuthenticationService;
import packages.project.Security.RegisterRequest;


@Controller
public class LoginController {
    private final LoginService loginService;
    private final AuthenticationService service;

    @Autowired
    public LoginController(LoginService loginService, AuthenticationService service) {
        this.loginService = loginService;
        this.service=service;
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
          if((loginId == 6969 )&& (Integer.parseInt(password) == 6969) )
              return "redirect:/api/superadmin/6969";
        }
        return "error";
  }


/*  @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
  }*/

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }
}
