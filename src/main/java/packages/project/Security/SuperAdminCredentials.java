package packages.project.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// The super admin is not stored in the database. Its login ID and PIN come from
// the SUPERADMIN_LOGIN_ID / SUPERADMIN_PIN environment variables, and the account
// is disabled when either is unset.
@Component
public class SuperAdminCredentials {

    private final String loginId;
    private final String pin;

    public SuperAdminCredentials(@Value("${transitflow.superadmin.login-id:}") String loginId,
                                 @Value("${transitflow.superadmin.pin:}") String pin) {
        this.loginId = loginId.trim();
        this.pin = pin.trim();
    }

    public boolean isEnabled() {
        return !loginId.isEmpty() && !pin.isEmpty();
    }

    public boolean matches(Integer loginId, Integer pin) {
        return isEnabled() && loginId != null && pin != null
                && this.loginId.equals(loginId.toString()) && this.pin.equals(pin.toString());
    }
}
