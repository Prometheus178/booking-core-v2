package org.booking.core.mapper;

import jakarta.inject.Inject;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.domain.entity.business.service.BusinessService;
import org.booking.core.domain.request.BusinessServiceRequest;
import org.booking.core.domain.response.BusinessServiceResponse;
import org.booking.core.repository.BusinessRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class BusinessServiceMapper {

    @Inject
    private BusinessRepository businessRepository;

    static BusinessServiceMapper INSTANCE = Mappers.getMapper(BusinessServiceMapper.class);


    @Mapping(source = "business", target = "businessId")
    public abstract BusinessServiceResponse toDto(BusinessService obj);

    public abstract BusinessService toEntity(BusinessServiceRequest dto);

    protected Long fromEntityToLong(Business business) {
        if (business == null){
            return null;
        }
        return business.getId();
    }
}
