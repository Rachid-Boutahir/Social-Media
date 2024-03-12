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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class GroupUserRepositoryTest {

    @Mock
    private GroupUserRepository groupUserRepository;

    @Mock
    private GroupUsers groupUsers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        groupUsers = GroupUsers.builder()
                .idGroupUser(1L)
                .idGroup(1L)
                .idUsers(1L)
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenFindByIdGroup_thenReturnGroupUsers() {
        when(groupUserRepository.findByIdGroup(anyLong())).thenReturn(Arrays.asList(groupUsers));

        List<GroupUsers> found = groupUserRepository.findByIdGroup(groupUsers.getIdGroup());

        assertEquals(1, found.size());
        assertEquals(groupUsers.getIdGroup(), found.get(0).getIdGroup());
    }

    @Test
    void whenFindByIdUsers_thenReturnGroupUsers() {
        when(groupUserRepository.findByIdUsers(anyLong())).thenReturn(Arrays.asList(groupUsers));

        List<GroupUsers> found = groupUserRepository.findByIdUsers(groupUsers.getIdUsers());

        assertEquals(1, found.size());
        assertEquals(groupUsers.getIdUsers(), found.get(0).getIdUsers());
    }

    @Test
    void whenFindByIdGroupAndIdUsers_thenReturnGroupUsers() {
        when(groupUserRepository.findByIdGroupAndIdUsers(anyLong(), anyLong())).thenReturn(groupUsers);

        GroupUsers found = groupUserRepository.findByIdGroupAndIdUsers(groupUsers.getIdGroup(), groupUsers.getIdUsers());

        assertEquals(groupUsers, found);
    }
}