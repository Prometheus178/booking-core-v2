package org.booking.core.mapper;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import org.booking.core.domain.entity.business.service.BusinessServiceEntity;
import org.booking.core.domain.entity.reservation.Reservation;
import org.booking.core.domain.request.ReservationRequest;
import org.booking.core.domain.response.ReservationResponse;
import org.booking.core.repository.BusinessServiceRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ReservationMapper {

	static ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

	@Inject
	private BusinessServiceRepository businessServiceRepository;

	@Mapping(source = "businessServiceEntity", target = "businessServiceId")
	public abstract ReservationResponse toResponse(Reservation obj);

	@Mapping(source = "businessServiceId", target = "businessServiceEntity")
	public abstract Reservation toEntity(ReservationRequest dto);


	protected BusinessServiceEntity fromLongToBusinessService(Long businessServiceId) throws EntityNotFoundException {
		if (businessServiceId == null) {
			return null;
		}
		return businessServiceRepository.findById(businessServiceId).orElseThrow(
				EntityNotFoundException::new);
	}

	protected Long fromBusinessServiceToLong(BusinessServiceEntity businessServiceEntity) throws EntityNotFoundException {
		if (businessServiceEntity == null) {
			return null;
		}
		return businessServiceEntity.getId();
	}

}
