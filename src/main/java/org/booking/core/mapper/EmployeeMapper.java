package org.booking.core.mapper;

import org.booking.core.domain.dto.EmployeeDto;
import org.booking.core.domain.dto.ReservationDto;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.domain.entity.employee.Employee;
import org.booking.core.domain.entity.reservation.Reservation;
import org.booking.core.repository.BusinessRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class EmployeeMapper {
    @Inject
    private BusinessRepository businessRepository;

    static EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(target = "reservationHistory", source = "reservationHistoryDto")
    @Mapping(target = "businesses", source = "businessIds", qualifiedByName = "mapBusinessIdsToBusinesses")
    public abstract Employee toEntity(EmployeeDto dto);

    @Mapping(target = "reservationHistoryDto", source = "reservationHistory")
    @Mapping(target = "businessIds", source = "businesses", qualifiedByName = "mapBusinessesToBusinessIds")
    public abstract EmployeeDto toDto(Employee entity);


    public abstract Set<Reservation> mapToEntitySet(Set<ReservationDto> dtoSet);

    public abstract Set<ReservationDto> mapToDtoSet(Set<Reservation> entitySet);

    @Named("mapBusinessIdsToBusinesses")
    protected Set<Business> mapBusinessIdsToBusinesses(Set<Long> businessIds) {
        return businessIds.stream()
                .map(this::fromLongToEntity)
                .collect(Collectors.toSet());
    }

    @Named("mapBusinessesToBusinessIds")
    protected Set<Long> mapBusinessesToBusinessIds(Set<Business> businesses) {
        return businesses.stream()
                .map(Business::getId)
                .collect(Collectors.toSet());
    }

    private Business fromLongToEntity(Long id) throws EntityNotFoundException {
        if (id == null){
            return null;
        }
        Optional<Business> optionalBusiness = businessRepository.findById(id);
        if (optionalBusiness.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return optionalBusiness.get();
    }


}
