package pl.falynsky.hospital_authorization_service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.falynsky.hospital_authorization_service.entities.Role;
import pl.falynsky.hospital_authorization_service.entities.User;
import pl.falynsky.hospital_authorization_service.repositories.RoleRepository;
import pl.falynsky.hospital_authorization_service.repositories.UserRepository;
import pl.falynsky.hospital_authorization_service.auth.jwt.JwtService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Role role = roleRepository.findByName("ROLE_USER");
        String requestPassword = request.getPassword();
        String encodedPassword = passwordEncoder.encode(requestPassword);
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encodedPassword)
                .roles(List.of(role))
                .enabled(true)
                .tokenExpired(false)
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
