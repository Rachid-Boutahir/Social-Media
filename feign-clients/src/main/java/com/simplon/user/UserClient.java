package com.simplon.user;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient("USER")
public interface UserClient {

    static final String DEFAULT_PAGE = "0";
    static final String DEFAULT_SIZE = "10";

    @PostMapping("/api/v1/users")
    ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto user);

    @GetMapping("/api/v1/users")
    ResponseEntity<Page<UserDto>> getAllUsers(
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_SIZE) int size);

    @GetMapping("/api/v1/users/{id}")
    ResponseEntity<UserDto> getUserById(@PathVariable Long id);

    @PutMapping("/api/v1/users/{id}")
    ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody @Valid UserDto user);

    @DeleteMapping("/api/v1/users/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable Long id);
}
