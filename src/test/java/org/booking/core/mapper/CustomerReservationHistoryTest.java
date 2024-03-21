package org.booking.core.mapper;

import org.booking.core.domain.entity.user.history.UserReservationHistory;
import org.booking.core.domain.request.UserReservationHistoryRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class CustomerReservationHistoryTest extends AbstractMapperTest<UserReservationHistory> {

    @Test
    void test() {
        UserReservationHistory userReservationHistory = createObject(UserReservationHistory.class);
		UserReservationHistoryRequest userReservationHistoryRequest = CustomerReservationHistoryMapper.INSTANCE.toDto(userReservationHistory);

		assertThat(userReservationHistoryRequest.getId()).isEqualTo(userReservationHistory.getId());
    }


}