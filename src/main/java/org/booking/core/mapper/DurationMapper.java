package org.booking.core.mapper;

import org.booking.core.domain.dto.DurationDto;
import org.booking.core.domain.entity.reservation.Duration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DurationMapper {

    DurationMapper INSTANCE = Mappers.getMapper(DurationMapper.class);

    DurationDto toDto(Duration obj);

    Duration dtoTo(DurationDto dto);

}
