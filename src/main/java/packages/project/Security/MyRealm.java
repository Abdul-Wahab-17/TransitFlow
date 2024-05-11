package packages.project.Security;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component; // Import the Component annotation
import packages.project.Login.Login;
import packages.project.Login.LoginService;

import java.util.Collections;
import java.util.Set;

@Component // Add the @Component annotation to make MyRealm a Spring-managed component
public class MyRealm extends JdbcRealm {

    private LoginService loginService; // Inject LoginService bean

    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        int loginId = Integer.parseInt(upToken.getUsername());

        // Retrieve user authentication info from the database
        Login user = loginService.findByLoginId(loginId);
        if (user != null) {
            // Compare the stored PIN with the token's password
            // You may need to adapt this part based on your database schema and hashing mechanism
            return new SimpleAuthenticationInfo(loginId, String.valueOf(user.getPin()), getName());
        }
        return null;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        int loginId = (int) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        // Retrieve user roles from the database
        Set<String> roles = Collections.singleton(loginService.getRole(loginId));
        authorizationInfo.setRoles(roles);

        // Add more authorization logic as needed, e.g., retrieving permissions

        return authorizationInfo;
    }
}
