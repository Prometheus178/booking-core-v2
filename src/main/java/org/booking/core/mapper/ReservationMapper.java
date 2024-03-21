package org.booking.core.mapper;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import org.booking.core.domain.entity.business.service.BusinessServiceEntity;
import org.booking.core.domain.entity.reservation.Reservation;
import org.booking.core.domain.entity.user.User;
import org.booking.core.domain.request.ReservationRequest;
import org.booking.core.domain.response.ReservationResponse;
import org.booking.core.repository.BusinessServiceRepository;
import org.booking.core.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ReservationMapper {

    static ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private BusinessServiceRepository businessServiceRepository;

	@Mapping(source = "businessServiceEntity", target = "businessServiceId")
    @Mapping(source = "employee", target = "employeeId")
public abstract ReservationResponse toDto(Reservation obj);

	@Mapping(source = "businessServiceId", target = "businessServiceEntity")
    @Mapping(source = "employeeId", target = "employee")
public abstract Reservation toEntity(ReservationRequest dto);

    protected User fromLongToUser(Long id) throws EntityNotFoundException {
        return getUser(id);
    }

	protected BusinessServiceEntity fromLongToBusinessService(Long businessServiceId) throws EntityNotFoundException {
		if (businessServiceId == null) {
            return null;
        }
		return businessServiceRepository.findById(businessServiceId).orElseThrow(
                EntityNotFoundException::new);
    }

    protected Long fromUserToLong(User employee) throws EntityNotFoundException {
        return getLong(employee);
    }

	protected Long fromBusinessServiceToLong(BusinessServiceEntity businessServiceEntity) throws EntityNotFoundException {
		if (businessServiceEntity == null) {
            return null;
        }
		return businessServiceEntity.getId();
    }

    private Long getLong(User employee) {
        if (employee == null) {
            return null;
        }
        return employee.getId();
    }

    private User getUser(Long customerId) {
        if (customerId == null) {
            return null;
        }
        return userRepository.findById(customerId).orElseThrow(
                EntityNotFoundException::new);
    }
}
