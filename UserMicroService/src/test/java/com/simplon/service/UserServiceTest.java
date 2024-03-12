package com.simplon.service;

import com.simplon.mapper.UserMapper;
import com.simplon.model.dto.UserDto;
import com.simplon.model.entity.UserEntity;
import com.simplon.model.enums.Gender;
import com.simplon.model.enums.Role;
import com.simplon.repository.UserRepository;
import com.simplon.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * User Service test class
 *
 * @Author Ayoub Ait Si Ahmad
 * @Date 2024-02-29
 */
@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDto userDto;
    private UserEntity userEntity;

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


        userEntity = UserEntity.builder()
                .idUser(2L)
                .firstName("test2")
                .lastName("test2")
                .email("test2@gmail.com")
                .username("test2")
                .password("Bonz567.//;@@")
                .gender(Gender.MALE)
                .bio("test2")
                .avatar("test2")
                .phone("test2")
                .build();

    }

    /**
     * Test for the method findByEmailIgnoreCaseOrUsernameIgnoreCase of UserRepository.
     * This test verifies that the method correctly finds a user by email or username, ignoring case.
     */
    @Test
    public void testCreateUser() {
        when(userMapper.userDtoToUserEntity(userDto)).thenReturn(userEntity);
        when(userRepository.findByEmailIgnoreCaseOrUsernameIgnoreCase(userEntity.getEmail(),
                userEntity.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.userEntityToUserDto(userEntity)).thenReturn(userDto);

        UserDto result = userService.createUser(userDto);

        assertEquals(userDto, result);
    }

    /**
     * Test for the method getUserById of UserService.
     * This test verifies that the method correctly retrieves a user by their ID.
     */
    @Test
    public void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userMapper.userEntityToUserDto(userEntity)).thenReturn(userDto);

        UserDto result = userService.getUserById(1L);

        assertEquals(userDto, result);
    }


    /**
     * Test for the method updateUser of UserService.
     * This test verifies that the method correctly updates a user.
     */
    @Test
    public void testUpdateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userMapper.userDtoToUserEntity(userDto)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.userEntityToUserDto(userEntity)).thenReturn(userDto);

        UserDto result = userService.updateUser(1L, userDto);

        assertEquals(userDto, result);
    }

    /**
     * Test for the method getAllUsers of UserService.
     * This test verifies that the method correctly retrieves all users.
     */
    @Test
    public void testGetAllUsers() {
        Page<UserEntity> userPage = new PageImpl<>(Collections.singletonList(userEntity));
        when(userRepository.findAll(PageRequest.of(0, 5))).thenReturn(userPage);
        when(userMapper.userEntityToUserDto(userEntity)).thenReturn(userDto);

        Page<UserDto> result = userService.getAllUsers(PageRequest.of(0, 5));

        assertEquals(1, result.getTotalElements());
        assertEquals(userDto, result.getContent().get(0));
    }
}