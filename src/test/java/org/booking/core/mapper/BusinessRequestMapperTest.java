package org.booking.core.mapper;

import org.booking.core.domain.entity.business.Business;
import org.booking.core.domain.response.BusinessResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class BusinessRequestMapperTest extends AbstractMapperTest<Business> {

    @Test
    void test() {
        Business business = createObject(Business.class);
        BusinessResponse businessResponse = BusinessMapper.INSTANCE.toDto(business);

        assertThat(businessResponse.getId()).isEqualTo(business.getId());
        assertThat(businessResponse.getDescription()).isEqualTo(business.getDescription());
       // assertThat(businessDto.getBusinessHours()).isEqualTo(business.getBusinessHours());
        assertThat(businessResponse.getType()).isEqualTo(business.getType().getName());
        assertThat(businessResponse.getAddress()).isEqualTo(business.getAddress());
      //  assertThat(businessDto.getReservationSchedule()).isEqualTo(business.getReservationSchedule());
    }


}