package org.booking.core.mapper;

import com.google.gson.Gson;
import org.booking.core.domain.dto.ReservationDto;
import org.booking.core.domain.dto.ReservationScheduleDto;
import org.booking.core.domain.entity.business.ReservationSchedule;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class ReservationScheduleMapperTest extends AbstractMapperTest<ReservationSchedule> {

    @Test
    void test() {
        ReservationSchedule reservationSchedule = createObject(ReservationSchedule.class);
        ReservationScheduleDto reservationScheduleDto = ReservationScheduleMapper.INSTANCE.toDto(reservationSchedule);

        assertThat(reservationScheduleDto.getId()).isEqualTo(reservationSchedule.getId());
       // assertThat(reservationScheduleDto.getBusiness()).isEqualTo(reservationSchedule.getBusiness());
        Map<LocalDate, Set<ReservationDto>> reservations = reservationScheduleDto.getReservations();
        Gson gson = new Gson();
        String json = gson.toJson(reservations);
        System.out.println(json);
    }


}