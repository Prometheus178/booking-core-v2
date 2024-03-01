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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = User.builder()
                .email(registerRequest.getEmail())
                .name(registerRequest.getName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
     authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()));

     var user = userRepository.findByEmail(authenticationRequest.getEmail())
             .orElseThrow(() -> new NotFoundException("Not found user"));

     var jwtToken = jwtService.generateToken(user);
     return AuthenticationResponse.builder()
             .token(jwtToken)
             .build();
    }
}
