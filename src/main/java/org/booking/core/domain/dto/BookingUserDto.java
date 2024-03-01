package org.booking.core.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingUserDto {


    private String name;
    private String email;
    private String password;

}
