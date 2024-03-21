package org.booking.core.mapper;

import org.booking.core.domain.entity.business.Business;
import org.booking.core.domain.entity.business.service.BusinessServiceEntity;
import org.booking.core.domain.response.BusinessServiceResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class BusinessRequestBusinessServiceMapperTestEntity extends AbstractMapperTest<BusinessServiceEntity> {

    @Test
    void test() {
		BusinessServiceEntity businessServiceEntity = createObject(BusinessServiceEntity.class);
		BusinessServiceResponse businessServiceRequest = BusinessServiceMapper.INSTANCE.toDto(businessServiceEntity);

		assertThat(businessServiceRequest.getId()).isEqualTo(businessServiceEntity.getId());
		assertThat(businessServiceRequest.getDescription()).isEqualTo(businessServiceEntity.getDescription());
        //assertThat(businessServiceDto.getBusiness()).isEqualTo(businessService.getBusiness());
		assertThat(businessServiceRequest.getName()).isEqualTo(businessServiceEntity.getName());
		assertThat(businessServiceRequest.getPrice()).isEqualTo(businessServiceEntity.getPrice().doubleValue());
		assertThat(businessServiceRequest.getDuration()).isEqualTo(businessServiceEntity.getDuration());
    }

    private Business getBusiness() {
        return Instancio.of(Business.class)
                .create();
    }

}