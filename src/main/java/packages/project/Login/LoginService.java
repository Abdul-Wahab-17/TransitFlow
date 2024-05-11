package packages.project.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class LoginService {

    private final LoginRepository loginRepository;

    @Autowired
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public Login authenticateUser(int loginId, int pin) {
        return loginRepository.findByLoginIdAndPin(loginId, pin);
    }

    public void save(Login loginInfo) {
        loginRepository.save(loginInfo);
    }

    public String getRole(Integer loginId){ return loginRepository.findRoleByLoginId(loginId);}

    public Login findByLoginId(Integer loginId){ return loginRepository.findByLoginId(loginId);}

}
