package org.booking.core.mapper;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import org.booking.core.domain.dto.CustomerReservationHistoryDto;
import org.booking.core.domain.dto.ReservationDto;
import org.booking.core.domain.entity.customer.Customer;
import org.booking.core.domain.entity.customer.history.CustomerReservationHistory;
import org.booking.core.domain.entity.reservation.Reservation;
import org.booking.core.repository.CustomerRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Optional;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class CustomerReservationHistoryMapper {
    @Inject
    private CustomerRepository repository;


    static CustomerReservationHistoryMapper INSTANCE = Mappers.getMapper(CustomerReservationHistoryMapper.class);
    @Mapping(source = "customer", target = "customerId")
    abstract CustomerReservationHistoryDto toDto(CustomerReservationHistory obj);

    @Mapping(source = "customerId", target = "customer")
    abstract CustomerReservationHistory dtoTo(CustomerReservationHistoryDto dto);
    abstract  Set<Reservation> mapToEntitySet(Set<ReservationDto> dtoSet);

    abstract Set<ReservationDto> mapToDtoSet(Set<Reservation> entitySet);


    protected Customer fromLongToEntity(Long id) throws EntityNotFoundException {
        Optional<Customer> optionalEntity = repository.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return optionalEntity.get();
    }

    protected Long fromEntityToLong(Customer obj) throws EntityNotFoundException {
        return obj.getId();
    }

}