package packages.project.Login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService implements UserDetailsService {

    private final LoginRepository loginRepository;

    public Login authenticateUser(int loginId, int pin) {
        return loginRepository.findByLoginIdAndPin(loginId, pin);
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
        Integer loginId;
        try {
            loginId = Integer.parseInt(username);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid username format");
        }

        Login user = loginRepository.findByLoginId(loginId);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with loginId: " + username);
        }
        return user;
    }
}
