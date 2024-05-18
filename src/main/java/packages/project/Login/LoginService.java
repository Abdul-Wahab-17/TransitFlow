package packages.project.Login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService/* implements UserDetailsService */{

    private final LoginRepository loginRepository;
   // private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    public Login authenticateUser(int loginId, int pin) {
        return loginRepository.findByLoginIdAndPin(loginId, pin);
    }

    public void save(Login loginInfo) {

       // loginInfo.setPin(Integer.parseInt(bCryptPasswordEncoder.encode(   String.format("" + loginInfo.getPin() )       )));
        loginRepository.save(loginInfo);
    }

    public String getRole(Integer loginId){
        return loginRepository.findRoleByLoginId(loginId);}

    public Login findByLoginId(Integer loginId){ return loginRepository.findByLoginId(loginId);}

/*    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        // Load user details from the database based on the login ID
        Login login = loginRepository.findByLoginId(Integer.parseInt(loginId));

        return org.springframework.security.core.userdetails.User
                .withUsername(String.format(""+login.getLoginId()))
                .password(String.valueOf(login.getPin()))
                .roles(login.getRole())
                .build();
    }*/
}
