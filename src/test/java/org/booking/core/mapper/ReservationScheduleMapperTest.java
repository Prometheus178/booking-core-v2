package org.booking.core.mapper;

import org.booking.core.domain.entity.business.ReservationSchedule;
import org.booking.core.domain.request.ReservationScheduleRequest;
import org.junit.jupiter.api.Test;


class ReservationScheduleMapperTest extends AbstractMapperTest<ReservationScheduleRequest> {

    @Test
    void test() {
		ReservationScheduleRequest reservationScheduleRequest = createObject(ReservationScheduleRequest.class);
		ReservationSchedule reservationSchedule = ReservationScheduleMapper.INSTANCE.toEntity(reservationScheduleRequest);

		}


}