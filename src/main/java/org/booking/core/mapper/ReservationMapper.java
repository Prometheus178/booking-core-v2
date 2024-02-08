package org.booking.core.mapper;

import org.booking.core.domain.dto.ReservationDto;
import org.booking.core.domain.entity.reservation.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    ReservationDto toDto(Reservation obj);

    Reservation dtoTo(ReservationDto dto);

}
