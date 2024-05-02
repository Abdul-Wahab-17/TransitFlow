package packages.project.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import packages.project.Login.Login;
import packages.project.Login.LoginRepository;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import packages.project.Login.Login;
import packages.project.Login.LoginRepository;

import java.util.Collections;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private LoginRepository loginRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Integer loginId = (Integer) authentication.getPrincipal();
        // Integer pin = (Integer) authentication.getCredentials(); // Comment out this line temporarily

        // Retrieve the user from the database based on the loginId
        Login login = loginRepository.findByLoginId(loginId);
        if (login == null) {
            throw new BadCredentialsException("Invalid loginId") {};
        }

        // For testing/debugging purposes, return successful authentication without password comparison
        return new UsernamePasswordAuthenticationToken(loginId, null, Collections.emptyList());
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}