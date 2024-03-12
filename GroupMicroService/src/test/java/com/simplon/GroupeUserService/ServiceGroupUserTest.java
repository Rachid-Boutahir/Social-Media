package com.simplon.GroupeUserService;

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
class ServiceGroupUserTest {

    @InjectMocks
    ServiceGroupUser serviceGroupUser;

    @Mock
    GroupUserRepository groupUserRepository;

    @Mock
    GroupUserMapper groupUserMapper;

    GroupUserDTO groupUserDTO;
    GroupUsers groupUsers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        groupUserDTO = new GroupUserDTO();
        groupUserDTO.setIdGroupUser(1L);
        groupUserDTO.setIdGroup(1L);
        groupUserDTO.setIdUsers(1L);

        groupUsers = new GroupUsers();
        groupUsers.setIdGroupUser(1L);
        groupUsers.setIdGroup(1L);
        groupUsers.setIdUsers(1L);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveGroupUser() {
        when(groupUserMapper.groupUserDTOToGroupUser(any(GroupUserDTO.class))).thenReturn(groupUsers);
        when(groupUserRepository.save(any(GroupUsers.class))).thenReturn(groupUsers);
        when(groupUserMapper.groupUserToGroupUserDTO(any(GroupUsers.class))).thenReturn(groupUserDTO);

        GroupUserDTO savedGroupUser = serviceGroupUser.saveGroupUser(groupUserDTO);

        assertEquals(groupUserDTO, savedGroupUser);

    }

    @Test
    void getAllUsersInGroup() {
        when(groupUserRepository.findByIdGroup(anyLong())).thenReturn(Arrays.asList(groupUsers));
        when(groupUserMapper.groupUserToGroupUserDTO(any(GroupUsers.class))).thenReturn(groupUserDTO);

        List<GroupUserDTO> groupUserList = serviceGroupUser.getAllUsersInGroup(1L);

        assertEquals(1, groupUserList.size());

    }

    @Test
    void getAllGroupsForUser() {
        when(groupUserRepository.findByIdUsers(anyLong())).thenReturn(Arrays.asList(groupUsers));
        when(groupUserMapper.groupUserToGroupUserDTO(any(GroupUsers.class))).thenReturn(groupUserDTO);

        List<GroupUserDTO> groupUserList = serviceGroupUser.getAllGroupsForUser(1L);

        assertEquals(1, groupUserList.size());
    }

    @Test
    void deleteUserFromGroup() {
        when(groupUserRepository.findByIdGroupAndIdUsers(anyLong(), anyLong())).thenReturn(groupUsers);
        doNothing().when(groupUserRepository).delete(any(GroupUsers.class));

        serviceGroupUser.deleteUserFromGroup(1L, 1L);


    }
}