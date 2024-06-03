package packages.project.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import packages.project.Login.Login;
import packages.project.Login.LoginRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        Login user = loginRepository.findByLoginId(Integer.parseInt(request.getUsername()));
        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(token).build();
    }


}
