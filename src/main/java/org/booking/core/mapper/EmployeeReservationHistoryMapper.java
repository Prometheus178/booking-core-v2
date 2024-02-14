package org.booking.core.mapper;

import org.booking.core.domain.dto.EmployeeReservationHistoryDto;
import org.booking.core.domain.entity.employee.history.EmployeeReservationHistory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmployeeReservationHistoryMapper {

    EmployeeReservationHistoryMapper INSTANCE = Mappers.getMapper(EmployeeReservationHistoryMapper.class);

    EmployeeReservationHistoryDto toDto(EmployeeReservationHistory obj);

    EmployeeReservationHistory dtoTo(EmployeeReservationHistoryDto dto);


}