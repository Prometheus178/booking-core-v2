package org.booking.core.mapper;

import org.booking.core.domain.entity.business.Business;
import org.booking.core.request.BusinessRequest;
import org.booking.core.response.BusinessResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class BusinessMapper {

	static BusinessMapper INSTANCE = Mappers.getMapper(BusinessMapper.class);

	public abstract Business toEntity(BusinessRequest businessRequest);

	@Mapping(source = "employees", target = "employees")
//            expression = "java(fromEntityEmployeeToLongEmployees(business.getEmployees()))")
	public abstract BusinessResponse toResponse(Business business);

//	protected Set<Long> fromEntityEmployeeToLongEmployees(Set<String> employees) {
//		if (employees == null || employees.isEmpty()) {
//			return null;
//		}
//		return employees.stream()
//				.collect(Collectors.toSet());
//	}
}
