package packages.project.Security;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroSessionConfig {

    @Bean
    public DefaultSessionManager sessionManager() {
        CustomSessionManager sessionManager = new CustomSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(false); // Disable session validation scheduler
        sessionManager.setSessionIdCookieEnabled(true); // Enable session ID cookie
        sessionManager.setSessionIdUrlRewritingEnabled(false); // Disable URL rewriting with session ID

        // Set the session timeout
        sessionManager.setGlobalSessionTimeout(1800000); // 30 minutes

        return sessionManager;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        securityManager.setSessionManager(sessionManager()); // Set the custom session manager
        return securityManager;
    }

    // Define your custom realm
    @Bean
    public Realm realm() {
        return new MyRealm();
    }
}
