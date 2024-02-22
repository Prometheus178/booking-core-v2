package org.booking.core.mapper;

import org.booking.core.domain.dto.CustomerReservationHistoryDto;
import org.booking.core.domain.entity.customer.history.CustomerReservationHistory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class CustomerReservationHistoryTest extends AbstractMapperTest<CustomerReservationHistory> {

    @Test
    void test() {
        CustomerReservationHistory customerReservationHistory = createObject(CustomerReservationHistory.class);
        CustomerReservationHistoryDto customerReservationHistoryDto = CustomerReservationHistoryMapper.INSTANCE.toDto(customerReservationHistory);

        assertThat(customerReservationHistoryDto.getId()).isEqualTo(customerReservationHistory.getId());
    }


}