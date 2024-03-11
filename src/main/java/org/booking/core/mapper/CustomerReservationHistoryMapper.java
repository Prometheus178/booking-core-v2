package org.booking.core.mapper;

import jakarta.inject.Inject;
import org.booking.core.domain.dto.UserReservationHistoryDto;
import org.booking.core.domain.dto.ReservationDto;
import org.booking.core.domain.entity.user.history.UserReservationHistory;
import org.booking.core.domain.entity.reservation.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class CustomerReservationHistoryMapper {


    static CustomerReservationHistoryMapper INSTANCE = Mappers.getMapper(CustomerReservationHistoryMapper.class);
    //@Mapping(source = "customer", target = "customerId")
    abstract UserReservationHistoryDto toDto(UserReservationHistory obj);

//@Mapping(source = "customerId", target = "customer")
    abstract UserReservationHistory dtoTo(UserReservationHistoryDto dto);
    abstract  Set<Reservation> mapToEntitySet(Set<ReservationDto> dtoSet);

    abstract Set<ReservationDto> mapToDtoSet(Set<Reservation> entitySet);


//    protected Customer fromLongToEntity(Long id) throws EntityNotFoundException {
//        Optional<Customer> optionalEntity = repository.findById(id);
//        if (optionalEntity.isEmpty()) {
//            throw new EntityNotFoundException();
//        }
//        return optionalEntity.get();
//    }

//    protected Long fromEntityToLong(Customer obj) throws EntityNotFoundException {
//        return obj.getId();
//    }

}