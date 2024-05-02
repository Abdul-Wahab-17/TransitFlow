package packages.project.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import packages.project.Login.LoginRepository;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginRepository loginRepository;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    public WebSecurityConfig(LoginRepository loginRepository, CustomAuthenticationProvider customAuthenticationProvider) {
        this.loginRepository = loginRepository;
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll() // Allow access to login page
                .antMatchers("/api/admin/**").hasRole("admin") // Restrict access to admin pages to users with role "ADMIN"
                .antMatchers("/api/customers/**").hasRole("customer") // Restrict access to customer pages to users with role "CUSTOMER"
                .antMatchers("/api/drivers/**").hasRole("driver") // Restrict access to driver pages to users with role "DRIVER"
                .antMatchers("/").permitAll() // Allow access to the home page
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
