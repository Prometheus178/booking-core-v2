package org.booking.core.mapper;

import org.booking.core.domain.dto.BusinessDto;
import org.booking.core.domain.entity.business.Business;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class BusinessMapperTest extends AbstractMapperTest<Business> {

    @Test
    void testBusiness() {
        Business business = createObject(Business.class);
        BusinessDto businessDto = BusinessMapper.INSTANCE.toDto(business);

        assertThat(businessDto.getId()).isEqualTo(business.getId());
        assertThat(businessDto.getDescription()).isEqualTo(business.getDescription());
       // assertThat(businessDto.getBusinessHours()).isEqualTo(business.getBusinessHours());
        assertThat(businessDto.getType()).isEqualTo(business.getType().getName());
        assertThat(businessDto.getAddress()).isEqualTo(business.getAddress());
      //  assertThat(businessDto.getReservationSchedule()).isEqualTo(business.getReservationSchedule());
    }


}