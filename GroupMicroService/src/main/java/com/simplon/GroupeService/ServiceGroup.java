package com.simplon.GroupeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceGroup implements IServiceGroup{

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMapper groupMapper;

    public GroupDTO saveGroup(GroupDTO groupDTO) {
        try {
            Group group = groupMapper.groupDTOToGroup(groupDTO);
            Group savedGroup = groupRepository.save(group);
            return groupMapper.groupToGroupDTO(savedGroup);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save group");
        }
    }

    public GroupDTO getGroupById(Long id) {
        Optional<Group> group = groupRepository.findById(id);
        if (group.isEmpty()) {
            throw new RuntimeException("Group id not found : " + id);
        }
        return groupMapper.groupToGroupDTO(group.get());
    }

    public List<GroupDTO> getAllGroups() {
        try {
            List<Group> groups = groupRepository.findAll();
            return groups.stream()
                    .map(groupMapper::groupToGroupDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve groups");
        }
    }

    public GroupDTO updateGroup(GroupDTO groupDTO) {
        try {
            Group group = groupMapper.groupDTOToGroup(groupDTO);
            Group updatedGroup = groupRepository.save(group);
            return groupMapper.groupToGroupDTO(updatedGroup);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update group");
        }
    }

    public void deleteGroup(Long id) {
        try {
            groupRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete group with id : " + id);
        }
    }
}