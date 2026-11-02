package packages.project.Login;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService  implements UserDetailsService{

    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;

    // Returns the login if the PIN matches its stored hash, otherwise null
    public Login authenticateUser(int loginId, int pin) {
        Login login = loginRepository.findByLoginId(loginId);
        if (login == null || login.getPinHash() == null) {
            return null;
        }
        return passwordEncoder.matches(String.valueOf(pin), login.getPinHash()) ? login : null;
    }

    // Create and save a login with a hashed PIN
    public Login createLogin(String role, int pin) {
        Login login = new Login();
        login.setRole(role);
        setPin(login, pin);
        return loginRepository.save(login);
    }

    public static boolean isValidPin(String pin) {
        return pin != null && pin.matches("\\d{4}");
    }

    // Replace the PIN of an existing login (not saved)
    public void setPin(Login login, int pin) {
        login.setPinHash(passwordEncoder.encode(String.valueOf(pin)));
    }

    public void save(Login loginInfo) {
        loginRepository.save(loginInfo);
    }

    public String getRole(Integer loginId) {
        return loginRepository.findRoleByLoginId(loginId);
    }

    public Login findByLoginId(Integer loginId) {
        return loginRepository.findByLoginId(loginId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login user = loginRepository.findByLoginId(Integer.parseInt(username));
        if (user == null){
            throw new UsernameNotFoundException("not fount");
        }
        return new User(user);
    }

  
}
