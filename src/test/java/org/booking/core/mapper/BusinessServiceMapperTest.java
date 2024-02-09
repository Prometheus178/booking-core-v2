package org.booking.core.mapper;

import org.booking.core.domain.dto.BusinessDto;
import org.booking.core.domain.dto.BusinessServiceDto;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.domain.entity.business.service.BusinessService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class BusinessServiceMapperTest extends AbstractMapperTest<BusinessService> {

    @Test
    void testBusiness() {
        BusinessService businessService = createObject(BusinessService.class);
        BusinessServiceDto businessServiceDto = BusinessServiceMapper.INSTANCE.toDto(businessService);

        assertThat(businessServiceDto.getId()).isEqualTo(businessService.getId());
        assertThat(businessServiceDto.getDescription()).isEqualTo(businessService.getDescription());
        //assertThat(businessServiceDto.getBusiness()).isEqualTo(businessService.getBusiness());
        assertThat(businessServiceDto.getName()).isEqualTo(businessService.getName());
        assertThat(businessServiceDto.getPrice()).isEqualTo(businessService.getPrice().doubleValue());
        assertThat(businessServiceDto.getDuration()).isEqualTo(businessService.getDuration());
    }

    private Business getBusiness() {
        return Instancio.of(Business.class)
                .create();
    }

}