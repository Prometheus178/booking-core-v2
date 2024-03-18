package org.booking.core.mapper;

import org.booking.core.domain.entity.reservation.Reservation;
import org.booking.core.domain.entity.user.User;
import org.booking.core.domain.request.CustomerRequest;
import org.booking.core.domain.request.ReservationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

	CustomerRequest toDto(User obj);

	Set<ReservationRequest> mapToDtoSet(Set<Reservation> entitySet);


}
