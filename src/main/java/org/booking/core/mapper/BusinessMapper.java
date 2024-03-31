package org.booking.core.mapper;

import org.booking.core.domain.entity.base.AbstractEntity;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.domain.request.BusinessRequest;
import org.booking.core.domain.response.BusinessResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class BusinessMapper {

	static BusinessMapper INSTANCE = Mappers.getMapper(BusinessMapper.class);

	public abstract Business toEntity(BusinessRequest businessRequest);

	@Mapping(source = "employees", target = "employees")
//            expression = "java(fromEntityEmployeeToLongEmployees(business.getEmployees()))")
	public abstract BusinessResponse toResponse(Business business);

	protected Set<Long> fromEntityEmployeeToLongEmployees(Set<User> employees) {
		if (employees == null || employees.isEmpty()) {
			return null;
		}
		return employees.stream()
				.map(AbstractEntity::getId)
				.collect(Collectors.toSet());
	}
}
