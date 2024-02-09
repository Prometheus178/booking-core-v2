package org.booking.core.mapper;

import org.booking.core.domain.dto.EmployeeReservationHistoryDto;
import org.booking.core.domain.dto.ReservationDto;
import org.booking.core.domain.entity.employee.history.EmployeeReservationHistory;
import org.booking.core.domain.entity.reservation.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EmployeeReservationHistoryMapper {

    EmployeeReservationHistoryMapper INSTANCE = Mappers.getMapper(EmployeeReservationHistoryMapper.class);

    EmployeeReservationHistoryDto toDto(EmployeeReservationHistory obj);

    EmployeeReservationHistory dtoTo(EmployeeReservationHistoryDto dto);

    Set<Reservation> mapToEntitySet(Set<ReservationDto> dtoSet);

    Set<ReservationDto> mapToDtoSet(Set<Reservation> entitySet);

    default Map<LocalDate, Set<Reservation>> mapToEntityMap(Map<LocalDate, Set<ReservationDto>> dtoMap) {
        return dtoMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> mapToEntitySet(e.getValue())));
    }

    default Map<LocalDate, Set<ReservationDto>> mapFromDtoMap(Map<LocalDate, Set<Reservation>> entityMap) {
        return entityMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> mapToDtoSet(e.getValue())));
    }
}