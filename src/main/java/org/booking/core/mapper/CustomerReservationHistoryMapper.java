package org.booking.core.mapper;

import org.booking.core.domain.entity.reservation.Reservation;
import org.booking.core.domain.entity.user.history.UserReservationHistory;
import org.booking.core.domain.request.ReservationRequest;
import org.booking.core.domain.request.UserReservationHistoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class CustomerReservationHistoryMapper {


    static CustomerReservationHistoryMapper INSTANCE = Mappers.getMapper(CustomerReservationHistoryMapper.class);
    //@Mapping(source = "customer", target = "customerId")
    abstract UserReservationHistoryRequest toDto(UserReservationHistory obj);

//@Mapping(source = "customerId", target = "customer")
abstract Set<Reservation> mapToEntitySet(Set<ReservationRequest> dtoSet);

    abstract Set<ReservationRequest> mapToDtoSet(Set<Reservation> entitySet);


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