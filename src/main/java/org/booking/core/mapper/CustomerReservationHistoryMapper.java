package org.booking.core.mapper;

import org.booking.core.domain.dto.CustomerReservationHistoryDto;
import org.booking.core.domain.entity.customer.history.CustomerReservationHistory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerReservationHistoryMapper {

    CustomerReservationHistoryMapper INSTANCE = Mappers.getMapper(CustomerReservationHistoryMapper.class);

    CustomerReservationHistoryDto toDto(CustomerReservationHistory obj);

    CustomerReservationHistory dtoTo(CustomerReservationHistoryDto dto);

}