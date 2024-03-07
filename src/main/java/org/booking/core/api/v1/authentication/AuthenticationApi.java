package org.booking.core.api.v1.authentication;

import lombok.RequiredArgsConstructor;
import org.booking.core.domain.dto.security.AuthenticationRequest;
import org.booking.core.domain.dto.security.AuthenticationResponse;
import org.booking.core.domain.dto.security.RegisterRequest;
import org.booking.core.service.security.AuthenticationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthenticationApi {

    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public  ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PutMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest){
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
}
