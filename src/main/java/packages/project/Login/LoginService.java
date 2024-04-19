package packages.project.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final LoginRepository loginRepository;

        @Autowired
        public LoginService(LoginRepository loginRepository) {
            this.loginRepository = loginRepository;
        }

        public boolean authenticate(int loginId, int pin) {
            // Retrieve the login record from the repository based on loginId and pin
            Login login = loginRepository.findByLoginIdAndPin(loginId, pin);

            // Check if the login record exists and is valid
            return login != null;
        }
    }
