package com.simplon.GroupeService;

import java.util.List;

public interface IServiceGroup {
    GroupDTO saveGroup(GroupDTO groupDTO);
    GroupDTO getGroupById(Long id);
    List<GroupDTO> getAllGroups();
    GroupDTO updateGroup(GroupDTO groupDTO);
    void deleteGroup(Long id);
}