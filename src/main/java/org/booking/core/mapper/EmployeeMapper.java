package org.booking.core.mapper;

import org.booking.core.domain.dto.EmployeeDto;
import org.booking.core.domain.dto.ReservationDto;
import org.booking.core.domain.entity.employee.Employee;
import org.booking.core.domain.entity.reservation.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(target = "reservationHistoryDto", source = "reservationHistory")
    EmployeeDto toDto(Employee obj);

    @Mapping(target = "reservationHistory", source = "reservationHistoryDto")
    Employee dtoTo(EmployeeDto dto);

    Set<Reservation> mapToEntitySet(Set<ReservationDto> dtoSet);

    Set<ReservationDto> mapToDtoSet(Set<Reservation> entitySet);


}
