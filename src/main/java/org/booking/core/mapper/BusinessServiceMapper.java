package org.booking.core.mapper;

import org.booking.core.domain.dto.BusinessServiceDto;
import org.booking.core.domain.dto.ReservationDto;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.domain.entity.business.service.BusinessService;
import org.booking.core.domain.entity.reservation.Reservation;
import org.booking.core.repository.BusinessRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class BusinessServiceMapper {

    @Autowired
    private BusinessRepository businessRepository;

    static BusinessServiceMapper INSTANCE = Mappers.getMapper(BusinessServiceMapper.class);

    @Mapping(source = "business", target = "businessId")
    public abstract BusinessServiceDto toDto(BusinessService obj);

    @Mapping(source = "businessId", target = "business")
    public abstract BusinessService dtoTo(BusinessServiceDto dto);

    abstract Set<Reservation> mapToEntitySet(Set<ReservationDto> dtoSet);

    abstract Set<ReservationDto> mapToDtoSet(Set<Reservation> entitySet);

    protected Map<LocalDate, Set<Reservation>> mapToEntityMap(Map<LocalDate, Set<ReservationDto>> dtoMap) {
        return dtoMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> mapToEntitySet(e.getValue())));
    }

    protected Map<LocalDate, Set<ReservationDto>> mapFromDtoMap(Map<LocalDate, Set<Reservation>> entityMap) {
        return entityMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> mapToDtoSet(e.getValue())));
    }

    protected Business fromLongToEntity(Long businessId) throws EntityNotFoundException {
        return businessRepository.findById(businessId).get();
    }

    protected Long fromEntityToLong(Business business) throws EntityNotFoundException {
        return business.getId();
    }
}
