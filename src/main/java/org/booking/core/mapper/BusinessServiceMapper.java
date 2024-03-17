package org.booking.core.mapper;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.domain.entity.business.service.BusinessService;
import org.booking.core.domain.request.BusinessServiceRequest;
import org.booking.core.domain.response.BusinessServiceResponse;
import org.booking.core.repository.BusinessRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class BusinessServiceMapper {

    @Inject
    private BusinessRepository businessRepository;

    static BusinessServiceMapper INSTANCE = Mappers.getMapper(BusinessServiceMapper.class);


    @Mapping(source = "business", target = "businessId")
    public abstract BusinessServiceResponse toDto(BusinessService obj);

    @Mapping(source = "businessId", target = "business")
    public abstract BusinessService toEntity(BusinessServiceRequest dto);


    protected Business fromLongToEntity(Long id) throws EntityNotFoundException {
        if (id == null){
            return null;
        }
        Optional<Business> optionalBusiness = businessRepository.findById(id);
        if (optionalBusiness.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return optionalBusiness.get();
    }

    protected Long fromEntityToLong(Business business) throws EntityNotFoundException {
        if (business == null){
            return null;
        }
        return business.getId();
    }
}
