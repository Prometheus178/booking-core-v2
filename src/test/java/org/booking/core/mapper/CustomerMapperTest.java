package org.booking.core.mapper;

import org.booking.core.domain.dto.CustomerDto;
import org.booking.core.domain.entity.customer.Customer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class CustomerMapperTest extends AbstractMapperTest<Customer> {

    @Test
    void test() {
        Customer customer = createObject(Customer.class);
        CustomerDto businessDto = CustomerMapper.INSTANCE.toDto(customer);

        assertThat(businessDto.getId()).isEqualTo(customer.getId());
      //  assertThat(businessDto.getEmail()).isEqualTo(customer.getEmail());
        //assertThat(businessDto.getName()).isEqualTo(customer.getName());
       // assertThat(businessDto.getReservationHistory()).isEqualTo(customer.getReservationHistory());

    }


}