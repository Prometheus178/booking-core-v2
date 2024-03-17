package org.booking.core.mapper;

import org.booking.core.domain.entity.business.ReservationSchedule;
import org.booking.core.domain.request.ReservationScheduleRequest;
import org.junit.jupiter.api.Test;


class ReservationScheduleMapperTest extends AbstractMapperTest<ReservationSchedule> {

    @Test
    void test() {
        ReservationSchedule reservationSchedule = createObject(ReservationSchedule.class);
		ReservationScheduleRequest reservationScheduleRequest = ReservationScheduleMapper.INSTANCE.toDto(reservationSchedule);

		assertThat(reservationScheduleRequest.getId()).isEqualTo(reservationSchedule.getId());
    }


}