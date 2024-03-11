package org.booking.core.mapper;

import org.booking.core.domain.dto.UserDto;
import org.booking.core.domain.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookingCoreMapper {

    BookingCoreMapper INSTANCE = Mappers.getMapper(BookingCoreMapper.class);

    UserDto toDto(User obj);

    User toEntity(UserDto dto);

}
