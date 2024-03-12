package com.simplon.GroupeService;

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
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroupController.class)
@ExtendWith(SpringExtension.class)
class GroupControllerTest {

    @MockBean
    private IServiceGroup groupService;

    @Autowired
    MockMvc mockMvc;

    GroupDTO groupDTO;

    @BeforeEach
    void setUp() {
        groupDTO = new GroupDTO();
        groupDTO.setIdAdmin(1L);
        groupDTO.setGroupName("Test Group");
        groupDTO.setGroupDescription("This is a test group");
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getAllGroups() throws Exception {
        when(groupService.getAllGroups()).thenReturn(List.of(groupDTO));
        mockMvc.perform(get("/api/group/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getGroupById() throws Exception {
        when(groupService.getGroupById(1L)).thenReturn(groupDTO);
        mockMvc.perform(get("/api/group/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createGroup() throws Exception {
        when(groupService.saveGroup(groupDTO)).thenReturn(groupDTO);
        mockMvc.perform(post("/api/group/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(groupDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void updateGroup() throws Exception {
        when(groupService.updateGroup(any(GroupDTO.class))).thenReturn(groupDTO);
        mockMvc.perform(put("/api/group/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(groupDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteGroup() throws Exception {
        mockMvc.perform(delete("/api/group/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}