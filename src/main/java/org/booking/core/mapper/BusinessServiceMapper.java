package org.booking.core.mapper;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.domain.entity.business.service.BusinessServiceEntity;
import org.booking.core.domain.request.BusinessServiceRequest;
import org.booking.core.domain.response.BusinessServiceResponse;
import org.booking.core.repository.BusinessRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class BusinessServiceMapper {

	static BusinessServiceMapper INSTANCE = Mappers.getMapper(BusinessServiceMapper.class);
	@Inject
	private BusinessRepository businessRepository;

	@Mapping(source = "business", target = "businessId")
	public abstract BusinessServiceResponse toDto(BusinessServiceEntity obj);

	@Mapping(source = "businessId", target = "business")
	public abstract BusinessServiceEntity toEntity(BusinessServiceRequest dto);

	protected Long fromEntityToLong(Business business) {
		if (business == null) {
			return null;
		}
		return business.getId();
	}

	protected Business fromLongToEntity(Long businessId) {
		if (businessId == null) {
			return null;
		}
		return businessRepository.findById(businessId).orElseThrow(
				EntityNotFoundException::new);
	}
}
