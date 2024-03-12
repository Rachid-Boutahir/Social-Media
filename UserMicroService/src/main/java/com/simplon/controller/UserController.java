package com.simplon.controller;

import com.simplon.model.dto.UserDto;
import com.simplon.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User controller to manage user data.
 *
 * @Author Ayoub Ait Si Ahmad
 * @Date 2024-02-28
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String DEFAULT_PAGE = "0";
    private static final String DEFAULT_SIZE = "10";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto user) {
        logger.info("Creating a new user with email: {}", user.getEmail());
        UserDto userDto = userService.createUser(user);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<UserDto>> getAllUsers(
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_SIZE) int size ) {

        logger.info("Fetching all users with pagination");
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<UserDto> users = userService.getAllUsers(pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        logger.info("Fetching user with id: {}", id);
        UserDto userDto = userService.getUserById(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody @Valid UserDto user) {
        logger.info("Updating user with id: {}", id);
        UserDto userDto = userService.updateUser(id, user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Deleting user with id: {}", id);
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @CircuitBreaker(name="Friend_Service", fallbackMethod = "getDefaultAcceptedFriends")
    @GetMapping("/friends")
    public ResponseEntity<String> getAllacceptedFriends() {
        logger.info("Fetching all accepted friends");
        String friends = userService.getAllacceptedFriends();
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    public ResponseEntity<String> getDefaultAcceptedFriends(Exception e){
        return new ResponseEntity<String>("Friends service is down", HttpStatus.OK);

    }
}