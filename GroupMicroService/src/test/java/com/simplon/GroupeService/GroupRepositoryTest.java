package com.simplon.GroupeService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class GroupRepositoryTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private Group group;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        group = Group.builder()
                .idGroup(1L)
                .idAdmin(1L)
                .groupName("Test Group")
                .groupDescription("This is a test group")
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenSave_thenReturnGroup() {
        when(groupRepository.save(any(Group.class))).thenReturn(group);

        Group saved = groupRepository.save(group);

        assertEquals(group, saved);
    }

    @Test
    void whenGetById_thenReturnGroup() {
        when(groupRepository.findById(anyLong())).thenReturn(Optional.of(group));

        Optional<Group> found = groupRepository.findById(group.getIdGroup());

        assertEquals(group, found.get());
    }

    @Test
    void whenDeleteById_thenDeletingShouldBeSuccessful() {
        doNothing().when(groupRepository).deleteById(anyLong());

        groupRepository.deleteById(group.getIdGroup());

        verify(groupRepository, times(1)).deleteById(group.getIdGroup());
    }
}