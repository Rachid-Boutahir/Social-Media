package com.simplon.service.impl;

import com.simplon.exception.CustomNotFoundException;
import com.simplon.friendship.FriendShipDto;
import com.simplon.friendship.FriendshipClient;
import com.simplon.mapper.UserMapper;
import com.simplon.model.dto.UserDto;
import com.simplon.model.entity.UserEntity;
import com.simplon.model.enums.Role;
import com.simplon.repository.UserRepository;
import com.simplon.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

/**
 * User Service implementation to manage user data.
 *
 * @Author Ayoub Ait Si Ahmad
 * @Date 2024-02-28
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FriendshipClient friendshipClient;
    private final UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           FriendshipClient friendshipClient) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.friendshipClient = friendshipClient;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        logger.info("Creating a new user with email: {}", userDto.getEmail());
        UserEntity userEntity = userMapper.userDtoToUserEntity(userDto);
        Optional<UserEntity> userEntityOptional = userRepository.findByEmailIgnoreCaseOrUsernameIgnoreCase(
                userEntity.getEmail(),userEntity.getUsername());
        if(userEntityOptional.isPresent()){
            logger.error("User already exists with same email and username");
            throw new CustomNotFoundException("User already exists with same email and username", HttpStatus.BAD_REQUEST);
        }
        userEntity.setRole(Role.USER);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        logger.info("User created with id: {}", savedUserEntity.getIdUser());
        return userMapper.userEntityToUserDto(savedUserEntity);
    }

    @Override
    public UserDto getUserById(Long id) {
        logger.info("Fetching user with id: {}", id);
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        if(userEntityOptional.isEmpty()){
            logger.error("User not found with id: {}", id);
            throw new CustomNotFoundException("User not found", HttpStatus.NOT_FOUND);
        }
        return userMapper.userEntityToUserDto(userEntityOptional.get());
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        logger.info("Updating user with id: {}", id);
        UserEntity userEntity = userMapper.userDtoToUserEntity(userDto);
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        UserEntity existingUserEntity = userEntityOptional.orElseThrow(() -> {
            logger.error("User not found with id: {}", id);
            return new CustomNotFoundException("User not found", HttpStatus.NOT_FOUND);
        });

        Optional<UserEntity> userEntityOptional1 = userRepository.findByEmailIgnoreCaseOrUsernameIgnoreCase(
                userEntity.getEmail(),userEntity.getUsername());
        if(userEntityOptional1.isPresent()){
            logger.error("User already exists with same email and username");
            throw new CustomNotFoundException("User already exists with same email and username", HttpStatus.BAD_REQUEST);
        }
        existingUserEntity.setEmail(userEntity.getEmail());
        existingUserEntity.setFirstName(userEntity.getFirstName());
        existingUserEntity.setLastName(userEntity.getLastName());
        existingUserEntity.setUsername(userEntity.getUsername());
        existingUserEntity.setPassword(userEntity.getPassword());
        existingUserEntity.setRole(userEntity.getRole());
        existingUserEntity.setAvatar(userEntity.getAvatar());
        existingUserEntity.setPhone(userEntity.getPhone());
        existingUserEntity.setBio(userEntity.getBio());
        UserEntity savedUserEntity = userRepository.save(existingUserEntity);
        logger.info("User updated with id: {}", savedUserEntity.getIdUser());
        return userMapper.userEntityToUserDto(savedUserEntity);
    }


    @Override
    public void deleteUser(Long id) {
        logger.info("Deleting user with id: {}", id);
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        if(userEntityOptional.isEmpty()){
            logger.error("User not found with id: {}", id);
            throw new CustomNotFoundException("User not found", HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
        logger.info("User deleted with id: {}", id);
    }

    @Override
    public Page<UserDto> getAllUsers(Pageable pageable) {
        logger.info("Fetching all users with pagination");
        Page<UserEntity> userEntityPage = userRepository.findAll(pageable);
        return userEntityPage.map(userMapper::userEntityToUserDto);
    }

    @Override
    public String getAllacceptedFriends() {
        logger.info("Fetching all accepted friends");
        List<FriendShipDto> friendShipDtoList = friendshipClient.getAllFriendAccepted().getBody();
        return friendShipDtoList.toString();
    }
}