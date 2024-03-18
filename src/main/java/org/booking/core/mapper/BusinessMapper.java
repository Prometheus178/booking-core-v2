package org.booking.core.mapper;

import org.booking.core.domain.entity.business.Business;
import org.booking.core.domain.request.BusinessRequest;
import org.booking.core.domain.response.BusinessResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BusinessMapper {

    BusinessMapper INSTANCE = Mappers.getMapper(BusinessMapper.class);

    Business dtoTo(BusinessRequest businessRequest);

    BusinessResponse toDto(Business business);

}
