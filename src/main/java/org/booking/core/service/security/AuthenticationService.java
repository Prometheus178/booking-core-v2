package org.booking.core.service.security;

import lombok.RequiredArgsConstructor;
import org.booking.core.config.security.JWTService;
import org.booking.core.domain.dto.security.AuthenticationRequest;
import org.booking.core.domain.dto.security.AuthenticationResponse;
import org.booking.core.domain.dto.security.RegisterRequest;
import org.booking.core.domain.entity.Role;
import org.booking.core.domain.entity.customer.User;
import org.booking.core.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new AuthenticationServiceException(String.format("User with email: %s exist", email));
        }
       // String salt = RandomStringUtils.random(10, true, false);
        var user = User.builder()
                .email(email)
                .name(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.valueOf(registerRequest.getRole())) //todo remove the ability to create Admin user
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        String email = authenticationRequest.getEmail();
        var user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("User with email %s not found", email))
                );

        boolean isEquals = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
        if (isEquals) {
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } else {
            throw new RuntimeException("password is incorrect");
        }
    }
}
