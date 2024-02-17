package org.booking.core.mapper;

import org.booking.core.domain.dto.BusinessServiceDto;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.domain.entity.business.service.BusinessService;
import org.booking.core.repository.BusinessRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class BusinessServiceMapper {

    @Inject
    private BusinessRepository businessRepository;

    static BusinessServiceMapper INSTANCE = Mappers.getMapper(BusinessServiceMapper.class);


    @Mapping(source = "business", target = "businessId")
    public abstract BusinessServiceDto toDto(BusinessService obj);

    @Mapping(source = "businessId", target = "business")
    public abstract BusinessService dtoTo(BusinessServiceDto dto);


    protected Business fromLongToEntity(Long businessId) throws EntityNotFoundException {
        Optional<Business> optionalBusiness = businessRepository.findById(businessId);
        if (optionalBusiness.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return optionalBusiness.get();
    }

    protected Long fromEntityToLong(Business business) throws EntityNotFoundException {
        return business.getId();
    }
}
