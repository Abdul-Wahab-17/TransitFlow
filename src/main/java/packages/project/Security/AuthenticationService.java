package packages.project.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import packages.project.Login.Login;
import packages.project.Login.LoginService;

@Component
public class AuthenticationService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    LoginService loginService;


    public String authenticateUser(String username, String password) {
        Login user = loginService.findByLoginId(Integer.parseInt(username));
        if (user == null || !(user.getPin()==(Integer.parseInt(password)))) {
            throw new UsernameNotFoundException("not found");
        }

        // Assuming the user has a role stored in the database
        String role = user.getRole();

        // Generate a JWT token
        return jwtTokenUtil.generateToken(username, role);
    }
}
