package org.booking.core.mapper;

import org.booking.core.domain.dto.BusinessServiceDto;
import org.booking.core.domain.entity.business.service.BusinessService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BusinessServiceMapper {

    BusinessServiceMapper INSTANCE = Mappers.getMapper(BusinessServiceMapper.class);

    BusinessServiceDto toDto(BusinessService obj);

    BusinessService dtoTo(BusinessServiceDto dto);

}
