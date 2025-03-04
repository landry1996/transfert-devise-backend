package com.landry.transfertdedevise.security.mapper;

import com.landry.transfertdedevise.security.dto.UserDto;
import com.landry.transfertdedevise.security.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
    @Mapping(source = "id", target = "userId")
    UserDto toDtoWithUserId(User user);
}