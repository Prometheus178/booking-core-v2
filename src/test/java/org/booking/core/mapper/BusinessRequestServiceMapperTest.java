package org.booking.core.mapper;

import org.booking.core.domain.entity.business.Business;
import org.booking.core.domain.entity.business.service.BusinessService;
import org.booking.core.domain.response.BusinessServiceResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class BusinessRequestServiceMapperTest extends AbstractMapperTest<BusinessService> {

    @Test
    void test() {
        BusinessService businessService = createObject(BusinessService.class);
        BusinessServiceResponse businessServiceRequest = BusinessServiceMapper.INSTANCE.toDto(businessService);

        assertThat(businessServiceRequest.getId()).isEqualTo(businessService.getId());
        assertThat(businessServiceRequest.getDescription()).isEqualTo(businessService.getDescription());
        //assertThat(businessServiceDto.getBusiness()).isEqualTo(businessService.getBusiness());
        assertThat(businessServiceRequest.getName()).isEqualTo(businessService.getName());
        assertThat(businessServiceRequest.getPrice()).isEqualTo(businessService.getPrice().doubleValue());
        assertThat(businessServiceRequest.getDuration()).isEqualTo(businessService.getDuration());
    }

    private Business getBusiness() {
        return Instancio.of(Business.class)
                .create();
    }

}