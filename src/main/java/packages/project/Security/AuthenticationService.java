package packages.project.Security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public Subject login(String username, String password) {
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true); // if desired, enable 'remember me' functionality
            try {
                currentUser.login(token);
                // Authentication successful
            } catch (IncorrectCredentialsException ice) {
                // Invalid password
            } catch (UnknownAccountException uae) {
                // Unknown username
            } catch (LockedAccountException lae) {
                // Locked account
            } catch (AuthenticationException ae) {
                // Other authentication error
            }
        }
        return currentUser;
    }

    public void logout() {
        SecurityUtils.getSubject().logout();
    }
}
