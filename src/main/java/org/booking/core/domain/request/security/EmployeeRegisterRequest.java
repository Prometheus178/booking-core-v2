package org.booking.core.domain.request.security;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRegisterRequest {


	private String name;
	private String email;

}
