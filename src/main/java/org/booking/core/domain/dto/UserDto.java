package org.booking.core.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {


    private String name;
    private String email;
    private String password;

}
