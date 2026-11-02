package packages.project.Driver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import packages.project.Login.Login;
import packages.project.Login.LoginService;
import packages.project.Security.AuthSession;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DriverEditPageTest {

    @Autowired MockMvc mockMvc;
    @Autowired LoginService loginService;
    @Autowired DriverRepository drivers;

    private MockHttpSession driverSession(int loginId) {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(AuthSession.LOGIN_ID, loginId);
        session.setAttribute(AuthSession.ROLE, "driver");
        return session;
    }

    private Login newDriver() {
        Login login = loginService.createLogin("driver", 1234);
        Driver d = new Driver();
        d.setName("Test Driver");
        d.setPhone("03001234567");
        d.setEmail("t@example.com");
        d.setAddress("Somewhere");
        d.setLogin(login);
        drivers.save(d);
        return login;
    }

    @Test
    void editPageRendersWithoutExposingTheStoredPinHash() throws Exception {
        Login login = newDriver();
        mockMvc.perform(get("/drivers/me/edit").session(driverSession(login.getLoginId())))
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("$2a$"))))
                .andExpect(content().string(containsString("Leave blank to keep your PIN")));
    }

    @Test
    void blankPinKeepsTheCurrentPinAndANewOneReplacesIt() throws Exception {
        Login login = newDriver();
        MockHttpSession session = driverSession(login.getLoginId());
        mockMvc.perform(post("/drivers/me/edit").session(session).with(csrf())
                .param("name", "Renamed").param("phone", "03007654321").param("email", "t@example.com")
                .param("address", "Elsewhere").param("pin", ""))
                .andExpect(status().is3xxRedirection());
        assertThat(loginService.authenticateUser(login.getLoginId(), 1234)).isNotNull();

        mockMvc.perform(post("/drivers/me/edit").session(session).with(csrf())
                .param("name", "Renamed").param("phone", "03007654321").param("email", "t@example.com")
                .param("address", "Elsewhere").param("pin", "9876"))
                .andExpect(status().is3xxRedirection());
        assertThat(loginService.authenticateUser(login.getLoginId(), 9876)).isNotNull();
        assertThat(loginService.authenticateUser(login.getLoginId(), 1234)).isNull();
    }

    @Test
    void aMalformedPinIsRejectedAndNothingChanges() throws Exception {
        Login login = newDriver();
        mockMvc.perform(post("/drivers/me/edit").session(driverSession(login.getLoginId())).with(csrf())
                .param("name", "X").param("phone", "1").param("email", "t@example.com").param("address", "Y").param("pin", "12"))
                .andExpect(redirectedUrl("/drivers/me/edit?pinError"));
        assertThat(loginService.authenticateUser(login.getLoginId(), 1234)).isNotNull();
    }

    @Test
    void unknownUrlsReturnNotFound() throws Exception {
        mockMvc.perform(get("/definitely-not-a-page")).andExpect(status().isNotFound());
    }
}
