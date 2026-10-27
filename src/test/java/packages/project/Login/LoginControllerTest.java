package packages.project.Login;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import packages.project.Security.AuthSession;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoginControllerTest {

    private final LoginService loginService = mock(LoginService.class);
    private final LoginController loginController = new LoginController(loginService);

    @Test
    void showLoginPageUsesExistingLowercaseTemplate() {
        Model model = new ExtendedModelMap();

        String view = loginController.showLoginPage(null, model);

        assertThat(view).isEqualTo("login");
    }

    @Test
    void showLoginPageAddsErrorMessageWhenRequested() {
        Model model = new ExtendedModelMap();

        String view = loginController.showLoginPage("true", model);

        assertThat(view).isEqualTo("login");
        assertThat(model.asMap()).containsKey("errorMessage");
    }

    @Test
    void authenticateRejectsNonNumericPin() {
        String view = loginController.authenticate(1001, "abc", new ExtendedModelMap(), new MockHttpServletRequest());

        assertThat(view).isEqualTo("redirect:/login?error");
        verify(loginService, never()).authenticateUser(1001, 0);
    }

    @Test
    void authenticateRedirectsDriverByRole() {
        Login login = new Login();
        login.setRole("driver");
        when(loginService.authenticateUser(1001, 1234)).thenReturn(login);
        MockHttpServletRequest request = new MockHttpServletRequest();

        String view = loginController.authenticate(1001, "1234", new ExtendedModelMap(), request);

        assertThat(view).isEqualTo("redirect:/dashboard");
        assertThat(request.getSession().getAttribute(AuthSession.LOGIN_ID)).isEqualTo(1001);
        assertThat(request.getSession().getAttribute(AuthSession.ROLE)).isEqualTo("driver");
    }

    @Test
    void authenticateAllowsSuperAdminFallback() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        String view = loginController.authenticate(6969, "6969", new ExtendedModelMap(), request);

        assertThat(view).isEqualTo("redirect:/api/superadmin/6969");
        assertThat(request.getSession().getAttribute(AuthSession.ROLE)).isEqualTo("superadmin");
    }
}
