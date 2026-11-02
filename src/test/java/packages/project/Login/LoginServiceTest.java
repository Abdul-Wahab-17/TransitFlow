package packages.project.Login;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginServiceTest {

    private final LoginRepository repository = mock(LoginRepository.class);
    private final LoginService loginService = new LoginService(repository, new BCryptPasswordEncoder(4));

    @Test
    void createLoginStoresHashNotPin() {
        when(repository.save(any(Login.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Login login = loginService.createLogin("driver", 1234);

        assertThat(login.getRole()).isEqualTo("driver");
        assertThat(login.getPinHash()).startsWith("$2a$").doesNotContain("1234");
    }

    @Test
    void authenticateChecksPinAgainstHash() {
        Login login = new Login();
        login.setLoginId(1001);
        loginService.setPin(login, 1234);
        when(repository.findByLoginId(1001)).thenReturn(login);

        assertThat(loginService.authenticateUser(1001, 1234)).isSameAs(login);
        assertThat(loginService.authenticateUser(1001, 9999)).isNull();
    }

    @Test
    void authenticateRejectsUnknownLogin() {
        assertThat(loginService.authenticateUser(4242, 1234)).isNull();
    }
}
