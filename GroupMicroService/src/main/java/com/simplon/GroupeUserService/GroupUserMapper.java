package com.simplon.GroupeUserService;

import com.simplon.GroupeService.Group;
import com.simplon.GroupeService.GroupDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface GroupUserMapper {
    com.simplon.GroupeUserService.GroupUserMapper INSTANCE = Mappers.getMapper(com.simplon.GroupeUserService.GroupUserMapper.class);

    GroupUserDTO groupUserToGroupUserDTO(GroupUsers groupUser);

    GroupUsers groupUserDTOToGroupUser(GroupUserDTO groupsUerDTO);
}
