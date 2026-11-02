package packages.project.Admin;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import packages.project.Area.AreaService;
import packages.project.Customer.CustomerService;
import packages.project.Driver.Driver;
import packages.project.Driver.DriverService;
import packages.project.Fee.FeeService;
import packages.project.Login.Login;
import packages.project.Login.LoginService;
import packages.project.Salary.Salary;
import packages.project.Salary.SalaryService;
import packages.project.Security.AuthSession;
import packages.project.Schedule.ScheduleService;
import packages.project.Vehicle.Vehicle;
import packages.project.Vehicle.VehicleService;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdminControllerTest {

    private final AdminService adminService = mock(AdminService.class);
    private final CustomerService customerService = mock(CustomerService.class);
    private final DriverService driverService = mock(DriverService.class);
    private final LoginService loginService = mock(LoginService.class);
    private final AreaService areaService = mock(AreaService.class);
    private final FeeService feeService = mock(FeeService.class);
    private final VehicleService vehicleService = mock(VehicleService.class);
    private final SalaryService salaryService = mock(SalaryService.class);
    private final ScheduleService scheduleService = mock(ScheduleService.class);

    private final AdminController adminController = new AdminController(
            adminService,
            customerService,
            driverService,
            loginService,
            areaService,
            feeService,
            vehicleService,
            salaryService,
            scheduleService
    );

    @Test
    void searchDriversWrapsSingleResultInList() {
        Driver driver = new Driver();
        when(driverService.getDriver(1001)).thenReturn(driver);
        Model model = new ExtendedModelMap();

        String view = adminController.searchDrivers("1001", adminSession(), model);

        assertThat(view).isEqualTo("admin_dashboard");
        assertThat(model.asMap().get("drivers")).asList().containsExactly(driver);
    }

    @Test
    void searchDriversRedirectsInvalidLoginId() {
        String view = adminController.searchDrivers("abc", adminSession(), new ExtendedModelMap());

        assertThat(view).isEqualTo("redirect:/");
    }

    @Test
    void searchDriversRejectsAnonymousUser() {
        String view = adminController.searchDrivers("1001", new MockHttpSession(), new ExtendedModelMap());

        assertThat(view).isEqualTo("redirect:/login?error");
    }

    @Test
    void editDriverAddsSalaryOptionsRequiredByTemplate() {
        Driver driver = new Driver();
        Login login = new Login();
        login.setLoginId(1001);
        driver.setLogin(login);
        Vehicle vehicle = new Vehicle();
        Salary salary = new Salary();
        when(driverService.getDriver(1001)).thenReturn(driver);
        when(adminService.getAllVehicles()).thenReturn(Collections.singletonList(vehicle));
        when(areaService.getAllAreas()).thenReturn(Collections.emptyList());
        when(salaryService.getAllSalaries()).thenReturn(Collections.singletonList(salary));
        Model model = new ExtendedModelMap();

        String view = adminController.editDriver(1001, adminSession(), model);

        assertThat(view).isEqualTo("admin_driver");
        assertThat(model.asMap().get("salaries")).asList().containsExactly(salary);
    }

    private MockHttpSession adminSession() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(AuthSession.LOGIN_ID, 9001);
        session.setAttribute(AuthSession.ROLE, "admin");
        return session;
    }
}
