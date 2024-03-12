package com.simplon.GroupeUserService;

import com.simplon.GroupeService.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceGroupUser implements IGroupUserService {

    @Autowired
    private GroupUserRepository groupUserRepository;

    @Autowired
    private GroupUserMapper groupUserMapper;

    public GroupUserDTO saveGroupUser(GroupUserDTO groupUserDTO) {
        try {
            if (groupUserRepository.findByIdGroupAndIdUsers(groupUserDTO.getIdGroup(), groupUserDTO.getIdUsers()) != null) {
                throw new RuntimeException("User with id " + groupUserDTO.getIdUsers() + " is already in group with id " + groupUserDTO.getIdGroup());
            }else {
            GroupUsers groupUsers = groupUserMapper.groupUserDTOToGroupUser(groupUserDTO);
            GroupUsers savedGroupUser = groupUserRepository.save(groupUsers);
            return groupUserMapper.groupUserToGroupUserDTO(savedGroupUser);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to save group user");
        }
    }

    @Override
    public List<GroupUserDTO> getAllUsersInGroup(Long groupId) {
        List<GroupUsers> groupUsers = groupUserRepository.findByIdGroup(groupId);
        if (groupUsers.isEmpty()) {
            throw new RuntimeException("Group id not found : " + groupId);
        }
        return groupUsers.stream()
                .map(groupUser -> groupUserMapper.groupUserToGroupUserDTO(groupUser))
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupUserDTO> getAllGroupsForUser(Long userId) {
        List<GroupUsers> groupUsers = groupUserRepository.findByIdUsers(userId);
        if (groupUsers.isEmpty()) {
            throw new RuntimeException("User id not found : " + userId);
        }
        return groupUsers.stream()
                .map(groupUser -> groupUserMapper.groupUserToGroupUserDTO(groupUser))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserFromGroup(Long groupId, Long userId) {
        GroupUsers groupUser = groupUserRepository.findByIdGroupAndIdUsers(groupId, userId);
        if (groupUser == null) {
            throw new RuntimeException("User id not found : " + userId);
        }
        try {
            groupUserRepository.delete(groupUser);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user from group");
        }
    }
}