package org.booking.core.service.security;

import org.booking.core.domain.request.security.AuthenticationRequest;
import org.booking.core.domain.request.security.AuthenticationResponse;
import org.booking.core.domain.request.security.BaseRegisterRequest;

public interface AuthenticationService {

	AuthenticationResponse register(BaseRegisterRequest baseRegisterRequest);

	AuthenticationResponse businessRegister(BaseRegisterRequest baseRegisterRequest);

	AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
