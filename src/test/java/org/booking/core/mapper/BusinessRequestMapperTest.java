package org.booking.core.mapper;

import org.booking.core.domain.request.BusinessRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class BusinessRequestMapperTest extends AbstractMapperTest<org.booking.core.domain.entity.business.Business> {

    @Test
    void test() {
        org.booking.core.domain.entity.business.Business business = createObject(org.booking.core.domain.entity.business.Business.class);
        BusinessRequest businessRequest = BusinessMapper.INSTANCE.toDto(business);

        assertThat(businessRequest.getId()).isEqualTo(business.getId());
        assertThat(businessRequest.getDescription()).isEqualTo(business.getDescription());
       // assertThat(businessDto.getBusinessHours()).isEqualTo(business.getBusinessHours());
        assertThat(businessRequest.getType()).isEqualTo(business.getType().getName());
        assertThat(businessRequest.getAddress()).isEqualTo(business.getAddress());
      //  assertThat(businessDto.getReservationSchedule()).isEqualTo(business.getReservationSchedule());
    }


}