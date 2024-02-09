package org.booking.core.mapper;

import org.booking.core.domain.dto.CustomerReservationHistoryDto;
import org.booking.core.domain.entity.customer.history.CustomerReservationHistory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class CustomerReservationHistoryTest extends AbstractMapperTest<CustomerReservationHistory> {

    @Test
    void testBusiness() {
        CustomerReservationHistory customerReservationHistory = createObject(CustomerReservationHistory.class);
        CustomerReservationHistoryDto customerReservationHistoryDto = CustomerReservationHistoryMapper.INSTANCE.toDto(customerReservationHistory);

        assertThat(customerReservationHistoryDto.getId()).isEqualTo(customerReservationHistory.getId());
        //assertThat(customerReservationHistoryDto.getCustomer()).isEqualTo(customerReservationHistory.getCustomer());
        assertThat(customerReservationHistoryDto.getDetails()).isEqualTo(customerReservationHistory.getDetails());
        assertThat(customerReservationHistoryDto.getEventTime()).isEqualTo(customerReservationHistory.getEventTime());
        assertThat(customerReservationHistoryDto.getEventType()).isEqualTo(customerReservationHistory.getEventType().name());
        //assertThat(customerReservationHistoryDto.getReservations()).isEqualTo(customerReservationHistory
        // .getReservations());
    }


}