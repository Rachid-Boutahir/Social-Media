package com.simplon.controller;

import com.simplon.exception.CustomNotFoundException;
import com.simplon.model.dto.UserDto;
import com.simplon.model.enums.Gender;
import com.simplon.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * User controller test class
 *
 * @Author Ayoub Ait Si Ahmad
 * @Date 2024-02-29
 */
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        userDto = UserDto.builder()
                .idUser(1L)
                .firstName("test")
                .lastName("test")
                .email("test@gmail.com")
                .username("test")
                .password("Bonz567.//;@@")
                .gender(Gender.MALE)
                .bio("test")
                .avatar("test")
                .phone("test")
                .build();
    }

    /**
     * Test the getAllUsers method of the UserController class
     * @throws Exception
     */
    @Test
    public void testGetAllUsers() throws Exception {
        Page<UserDto> userDtoPage = new PageImpl<>(Collections.singletonList(userDto));
        when(userService.getAllUsers(any(PageRequest.class))).thenReturn(userDtoPage);

        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].idUser").value(userDto.getIdUser()))
                .andExpect(jsonPath("$.content[0].email").value(userDto.getEmail()))
                .andExpect(jsonPath("$.content[0].username").value(userDto.getUsername()));
    }

    /**
     * Test the getUserById method of the UserController class
     * @throws Exception
     */
    @Test
    public void testGetUserById() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(userDto);

        mockMvc.perform(get("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUser").value(userDto.getIdUser()))
                .andExpect(jsonPath("$.email").value(userDto.getEmail()))
                .andExpect(jsonPath("$.username").value(userDto.getUsername()));
    }

    /**
     * Test the getUserById method of the UserController class when the user is not found
     * @throws Exception
     */
    @Test
    public void testGetUserByIdNotFound() throws Exception {
        when(userService.getUserById(anyLong())).thenThrow(new CustomNotFoundException("User not found", HttpStatus.NOT_FOUND));
        mockMvc.perform(get("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Test the createUser method of the UserController class
     * @throws Exception
     */
    @Test
    public void testCreateUser() throws Exception {
        when(userService.createUser(any())).thenReturn(userDto);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idUser\":1,\"firstName\":\"test\",\"lastName\":\"test\",\"email\":\"test@gmail.com\",\"username\":\"test\",\"password\":\"Bonz567.//;@@\",\"gender\":\"MALE\",\"bio\":\"test\",\"avatar\":\"test\",\"phone\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idUser").value(userDto.getIdUser()))
                .andExpect(jsonPath("$.email").value(userDto.getEmail()))
                .andExpect(jsonPath("$.username").value(userDto.getUsername()));
    }

    /**
     * Test the updateUser method of the UserController class
     * @throws Exception
     */
    @Test
    public void testUpdateUser() throws Exception {
        when(userService.updateUser(anyLong(), any())).thenReturn(userDto);

        mockMvc.perform(put("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idUser\":1,\"firstName\":\"test\",\"lastName\":\"test\",\"email\":\"test@gmail.com\",\"username\":\"test\",\"password\":\"Bonz567.//;@@\",\"gender\":\"MALE\",\"bio\":\"test\",\"avatar\":\"test\",\"phone\":\"test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUser").value(userDto.getIdUser()))
                .andExpect(jsonPath("$.email").value(userDto.getEmail()))
                .andExpect(jsonPath("$.username").value(userDto.getUsername()));
    }

    /**
     * Test the deleteUser method of the UserController class
     * @throws Exception
     */
    @Test
    public void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(anyLong());

        mockMvc.perform(delete("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}