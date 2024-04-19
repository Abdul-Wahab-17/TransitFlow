package packages.project.Security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll() // Permit access to all URLs
                .anyRequest().authenticated() // Require authentication for other URLs
                .and()
                .formLogin() // Enable form-based login
                .loginPage("/login").permitAll() // Specify custom login page URL
                .and()
                .logout() // Enable logout
                .logoutUrl("/logout").permitAll(); // Specify custom logout URL

        // Disable CSRF protection (not recommended for production without proper CSRF protection)
        http.csrf().disable();
    }
}
