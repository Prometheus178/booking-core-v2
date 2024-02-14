package org.booking.core.mapper;

import org.booking.core.domain.dto.CustomerReservationHistoryDto;
import org.booking.core.domain.dto.ReservationDto;
import org.booking.core.domain.entity.customer.history.CustomerReservationHistory;
import org.booking.core.domain.entity.reservation.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CustomerReservationHistoryMapper {

    CustomerReservationHistoryMapper INSTANCE = Mappers.getMapper(CustomerReservationHistoryMapper.class);

    CustomerReservationHistoryDto toDto(CustomerReservationHistory obj);

    CustomerReservationHistory dtoTo(CustomerReservationHistoryDto dto);
    Set<Reservation> mapToEntitySet(Set<ReservationDto> dtoSet);

    Set<ReservationDto> mapToDtoSet(Set<Reservation> entitySet);




}