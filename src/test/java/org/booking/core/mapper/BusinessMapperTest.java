package org.booking.core.mapper;

import org.booking.core.domain.dto.BusinessDto;
import org.booking.core.domain.entity.business.Business;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class BusinessMapperTest {

    @Test
    void testBusiness() {
        Business business = getBusiness();
        BusinessDto businessDto = BusinessMapper.INSTANCE.toDto(business);

        assertThat(businessDto.getId()).isEqualTo(business.getId());
        assertThat(businessDto.getDescription()).isEqualTo(business.getDescription());
    }

    private Business getBusiness() {
        return Instancio.of(Business.class)
                .create();
    }

}