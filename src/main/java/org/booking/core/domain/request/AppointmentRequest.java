package org.booking.core.domain.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AppointmentRequest {

    private long businessId;

    private long businessServiceId;

    private long day;
    private long dayTime;
    private long reservationId;

}
