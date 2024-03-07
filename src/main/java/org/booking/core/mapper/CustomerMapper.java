package org.booking.core.mapper;

import org.booking.core.domain.dto.CustomerDto;
import org.booking.core.domain.dto.ReservationDto;
import org.booking.core.domain.entity.reservation.Reservation;
import org.booking.core.domain.entity.user.User;
import org.mapstruct.factory.Mappers;

import java.util.Set;

//@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDto toDto(User obj);

    User toEntity(CustomerDto dto);

    Set<Reservation> mapToEntitySet(Set<ReservationDto> dtoSet);

    Set<ReservationDto> mapToDtoSet(Set<Reservation> entitySet);


}
