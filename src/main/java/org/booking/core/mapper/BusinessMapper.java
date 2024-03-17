package org.booking.core.mapper;

import org.booking.core.domain.request.BusinessRequest;
import org.booking.core.domain.response.BusinessResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BusinessMapper {

    BusinessMapper INSTANCE = Mappers.getMapper(BusinessMapper.class);

    org.booking.core.domain.entity.business.Business dtoTo(BusinessRequest businessRequest);

    BusinessResponse toDto(org.booking.core.domain.entity.business.Business business);

}
