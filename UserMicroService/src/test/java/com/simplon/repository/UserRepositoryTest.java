package com.simplon.repository;

import com.simplon.model.entity.UserEntity;
import com.simplon.model.enums.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * User Repository test class to manage user data.
 *
 * @Author Ayoub Ait Si Ahmad
 * @Date 2024-02-29
 */
@SpringBootTest
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    private UserEntity user;

    // before each test, we create a new user
    @BeforeEach
    public void setUp() {
        user = UserEntity.builder()
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
     * Test for the method findByEmailIgnoreCaseOrUsernameIgnoreCase of UserRepository.
     * This test verifies that the method correctly finds a user by email or username, ignoring case.
     */
    @Test
    public void testFindByEmailIgnoreCaseOrUsernameIgnoreCase() {

        when(userRepository.findByEmailIgnoreCaseOrUsernameIgnoreCase(user.getEmail(), user.getUsername())).thenReturn(Optional.of(user));

        Optional<UserEntity> foundUser = userRepository.findByEmailIgnoreCaseOrUsernameIgnoreCase(user.getEmail(), user.getUsername());

        assertTrue(foundUser.isPresent());
        assertEquals(user.getEmail(), foundUser.get().getEmail());
        assertEquals(user.getUsername(), foundUser.get().getUsername());
    }

    /**
     * Test for the method findAll of UserRepository.
     * This test verifies that the method correctly finds all users.
     */
    @Test
    public void testFindAll() {


        Page<UserEntity> userPage = new PageImpl<>(Collections.singletonList(user));

        when(userRepository.findAll(PageRequest.of(0, 5))).thenReturn(userPage);

        Page<UserEntity> foundUsers = userRepository.findAll(PageRequest.of(0, 5));

        assertEquals(1, foundUsers.getTotalElements());
        assertEquals(user, foundUsers.getContent().get(0));
    }
}