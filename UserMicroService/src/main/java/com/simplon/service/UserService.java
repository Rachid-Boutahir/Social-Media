package com.simplon.service;

import com.simplon.model.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * User Service interface to manage user data.
 *
 * @Author Ayoub Ait Si Ahmad
 * @Date 2024-02-28
 */
public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(Long id);
    UserDto updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
    Page<UserDto> getAllUsers(Pageable pageable);

    String getAllacceptedFriends();
}
