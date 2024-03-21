package org.booking.core.mapper;

import org.booking.core.domain.entity.reservation.Duration;
import org.booking.core.domain.request.DurationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DurationMapper {

	DurationMapper INSTANCE = Mappers.getMapper(DurationMapper.class);

	DurationRequest toDto(Duration obj);

	Duration toEntity(DurationRequest dto);

}
