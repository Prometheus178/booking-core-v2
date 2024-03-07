package org.booking.core.mapper;

import org.booking.core.domain.dto.UserReservationHistoryDto;
import org.booking.core.domain.entity.user.history.UserReservationHistory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class CustomerReservationHistoryTest extends AbstractMapperTest<UserReservationHistory> {

    @Test
    void test() {
        UserReservationHistory userReservationHistory = createObject(UserReservationHistory.class);
        UserReservationHistoryDto userReservationHistoryDto = CustomerReservationHistoryMapper.INSTANCE.toDto(userReservationHistory);

        assertThat(userReservationHistoryDto.getId()).isEqualTo(userReservationHistory.getId());
    }


}