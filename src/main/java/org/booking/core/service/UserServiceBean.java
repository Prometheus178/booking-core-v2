package org.booking.core.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceBean implements UserService {
	public static final String AUTHORIZATION = "Authorization";

	private final HttpServletRequest request;

	@Override
	public String getCurrentUserEmail() {
		return request.getHeader(AUTHORIZATION);
	}

}
