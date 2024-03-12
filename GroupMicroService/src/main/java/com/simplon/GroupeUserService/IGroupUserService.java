package com.simplon.GroupeUserService;

import java.util.List;

public interface IGroupUserService {
    GroupUserDTO saveGroupUser(GroupUserDTO groupUserDTO);
    List<GroupUserDTO> getAllUsersInGroup(Long groupId);
    List<GroupUserDTO> getAllGroupsForUser(Long userId);
    void deleteUserFromGroup(Long groupId, Long userId);
}