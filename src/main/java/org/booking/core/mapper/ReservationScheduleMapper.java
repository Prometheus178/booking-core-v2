package org.booking.core.mapper;

import org.booking.core.domain.dto.ReservationScheduleDto;
import org.booking.core.domain.entity.business.ReservationSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReservationScheduleMapper {

    ReservationScheduleMapper INSTANCE = Mappers.getMapper(ReservationScheduleMapper.class);

    ReservationScheduleDto toDto(ReservationSchedule obj);
    ReservationSchedule dtoTo(ReservationScheduleDto dto);


}
