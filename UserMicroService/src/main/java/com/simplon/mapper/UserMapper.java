package com.simplon.mapper;

import com.simplon.model.dto.UserDto;
import com.simplon.model.entity.UserEntity;
import org.mapstruct.Mapper;

/**
 * User Mapper to manage user data.
 *
 * @Author Ayoub Ait Si Ahmad
 * @Date 2024-02-28
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity userDtoToUserEntity(UserDto userDto);
    UserDto userEntityToUserDto(UserEntity userEntity);
}