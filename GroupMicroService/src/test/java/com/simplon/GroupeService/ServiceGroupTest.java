package com.simplon.GroupeService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class ServiceGroupTest {

    @InjectMocks
    ServiceGroup serviceGroup;

    @Mock
    GroupRepository groupRepository;

    @Mock
    GroupMapper groupMapper;

    GroupDTO groupDTO;
    Group group;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        groupDTO = new GroupDTO();
        groupDTO.setIdGroup(1L);
        groupDTO.setIdAdmin(1L);
        groupDTO.setGroupName("Test Group");
        groupDTO.setGroupDescription("This is a test group");

        group = new Group();
        group.setIdGroup(1L);
        group.setIdAdmin(1L);
        group.setGroupName("Test Group");
        group.setGroupDescription("This is a test group");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveGroup() {
        when(groupMapper.groupDTOToGroup(any(GroupDTO.class))).thenReturn(group);
        when(groupRepository.save(any(Group.class))).thenReturn(group);
        when(groupMapper.groupToGroupDTO(any(Group.class))).thenReturn(groupDTO);

        GroupDTO savedGroup = serviceGroup.saveGroup(groupDTO);

        assertEquals(groupDTO, savedGroup);

    }

    @Test
    void getGroupById() {
        when(groupRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(group));
        when(groupMapper.groupToGroupDTO(any(Group.class))).thenReturn(groupDTO);

        GroupDTO foundGroup = serviceGroup.getGroupById(1L);

        assertEquals(groupDTO, foundGroup);
    }

    @Test
    void getAllGroups() {
        when(groupRepository.findAll()).thenReturn(Arrays.asList(group));
        when(groupMapper.groupToGroupDTO(any(Group.class))).thenReturn(groupDTO);

        List<GroupDTO> groupList = serviceGroup.getAllGroups();

        assertEquals(1, groupList.size());

    }

    @Test
    void deleteGroup() {
        doNothing().when(groupRepository).deleteById(anyLong());

        serviceGroup.deleteGroup(1L);
    }

    @Test
    void updateGroup() {
        when(groupMapper.groupDTOToGroup(any(GroupDTO.class))).thenReturn(group);
        when(groupRepository.save(any(Group.class))).thenReturn(group);
        when(groupMapper.groupToGroupDTO(any(Group.class))).thenReturn(groupDTO);

        GroupDTO updatedGroup = serviceGroup.updateGroup(groupDTO);

        assertEquals(groupDTO, updatedGroup);
    }
}