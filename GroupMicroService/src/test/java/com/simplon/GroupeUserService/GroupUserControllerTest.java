package com.simplon.GroupeUserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroupUserController.class)
@ExtendWith(SpringExtension.class)
class GroupUserControllerTest {

    @MockBean
    private IGroupUserService groupUserService;

    @Autowired
    MockMvc mockMvc;

    GroupUserDTO groupUserDTO;

    @BeforeEach
    void setUp() {
        groupUserDTO = new GroupUserDTO();
        groupUserDTO.setIdGroupUser(1L);
        groupUserDTO.setIdGroup(1L);
        groupUserDTO.setIdUsers(1L);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getAllUsersInGroup() throws Exception {
        when(groupUserService.getAllUsersInGroup(1L)).thenReturn(List.of(groupUserDTO));
        mockMvc.perform(get("/api/groupUser/group/1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllGroupsForUser() throws Exception {
        when(groupUserService.getAllGroupsForUser(1L)).thenReturn(List.of(groupUserDTO));
        mockMvc.perform(get("/api/groupUser/user/1/groups")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addUserToGroup() throws Exception {
        when(groupUserService.saveGroupUser(any(GroupUserDTO.class))).thenReturn(groupUserDTO);
        mockMvc.perform(post("/api/groupUser/addGroup/1/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(groupUserDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteUserFromGroup() throws Exception {
        mockMvc.perform(delete("/api/groupUser/deleteGroup/1/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}