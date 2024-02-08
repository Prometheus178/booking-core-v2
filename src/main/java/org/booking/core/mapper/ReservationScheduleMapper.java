package org.booking.core.mapper;

import org.booking.core.domain.dto.ReservationDto;
import org.booking.core.domain.dto.ReservationScheduleDto;
import org.booking.core.domain.entity.business.ReservationSchedule;
import org.booking.core.domain.entity.reservation.Reservation;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ReservationScheduleMapper {

    ReservationScheduleMapper INSTANCE = Mappers.getMapper(ReservationScheduleMapper.class);
   // @MapMapping()
    ReservationScheduleDto toDto(ReservationSchedule obj);
   // @MapMapping(target = "reservations", source = "reservations")
    ReservationSchedule dtoTo(ReservationScheduleDto dto);

}
