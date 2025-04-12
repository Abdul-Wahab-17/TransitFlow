package packages.project.Login;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService  implements UserDetailsService{

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
        Login user = loginRepository.findByLoginId(Integer.parseInt(username));
        if (user == null){
            throw new UsernameNotFoundException("not fount");
        }
        return new User(user);
    }

  
}
