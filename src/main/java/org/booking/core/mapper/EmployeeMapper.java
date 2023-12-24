package org.booking.core.mapper;

import org.booking.core.domain.dto.EmployeeDto;
import org.booking.core.domain.entity.employee.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDto toDto(Employee obj);

    Employee dtoTo(EmployeeDto dto);

}
