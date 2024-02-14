package org.booking.core.mapper;

import org.booking.core.domain.dto.BusinessServiceDto;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.domain.entity.business.service.BusinessService;
import org.booking.core.repository.BusinessRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;

@Mapper(componentModel = "spring")
public abstract class BusinessServiceMapper {

    @Autowired
    private BusinessRepository businessRepository;

    static BusinessServiceMapper INSTANCE = Mappers.getMapper(BusinessServiceMapper.class);

    @Mapping(source = "business", target = "businessId")
    public abstract BusinessServiceDto toDto(BusinessService obj);

    @Mapping(source = "businessId", target = "business")
    public abstract BusinessService dtoTo(BusinessServiceDto dto);


    protected Business fromLongToEntity(Long businessId) throws EntityNotFoundException {
        return businessRepository.findById(businessId).get();
    }

    protected Long fromEntityToLong(Business business) throws EntityNotFoundException {
        return business.getId();
    }
}
