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
                .and()
                .formLogin()
                .loginPage("/login").permitAll() // Custom login page URL
                .loginProcessingUrl("/login")    // URL to submit the login form
                .successHandler((request, response, authentication) -> {
                    // Customize success handler if needed
                })
                .failureHandler((request, response, exception) -> {
                    // Customize failure handler if needed
                })
                .and()
                .logout()
                .logoutUrl("/logout").permitAll(); // Custom logout URL

        // Disable CSRF protection (not recommended for production without proper CSRF protection)
        http.csrf().disable();
    }
}
