package org.booking.core.mapper;

import org.booking.core.domain.dto.BusinessDto;
import org.booking.core.domain.entity.business.Business;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BusinessMapper {

    BusinessMapper INSTANCE = Mappers.getMapper(BusinessMapper.class);

    BusinessDto toDto(Business business);
    Business dtoTo(BusinessDto businessDto);

}
