package org.booking.core.mapper;

import org.booking.core.domain.dto.ReservationScheduleDto;
import org.booking.core.domain.entity.business.ReservationSchedule;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class ReservationScheduleMapperTest extends AbstractMapperTest<ReservationSchedule> {

    @Test
    void test() {
        ReservationSchedule reservationSchedule = createObject(ReservationSchedule.class);
        ReservationScheduleDto reservationScheduleDto = ReservationScheduleMapper.INSTANCE.toDto(reservationSchedule);

        assertThat(reservationScheduleDto.getId()).isEqualTo(reservationSchedule.getId());
       // assertThat(reservationScheduleDto.getBusiness()).isEqualTo(reservationSchedule.getBusiness());
    }


}