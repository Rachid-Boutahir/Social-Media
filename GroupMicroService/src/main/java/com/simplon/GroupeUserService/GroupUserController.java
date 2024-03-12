package com.simplon.GroupeUserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/api/groupUser")
public class GroupUserController {

    @Autowired
    private IGroupUserService groupUserService;

    @GetMapping("/group/{groupId}/users")
    public ResponseEntity<List<GroupUserDTO>> getAllUsersInGroup(@PathVariable Long groupId) {
        log.info("getAllUsersInGroup : groupId = " + groupId);
        List<GroupUserDTO> groupUsersDTOs = groupUserService.getAllUsersInGroup(groupId);
        log.info("Retrieved " + groupUsersDTOs.size() + " users in group with id " + groupId);
        return new ResponseEntity<>(groupUsersDTOs, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/groups")
    public ResponseEntity<List<GroupUserDTO>> getAllGroupsForUser(@PathVariable Long userId) {
        log.info("getAllGroupsForUser : userId = " + userId);
        List<GroupUserDTO> groupUsersDTOs = groupUserService.getAllGroupsForUser(userId);
        log.info("Retrieved " + groupUsersDTOs.size() + " groups for user with id " + userId);
        return new ResponseEntity<>(groupUsersDTOs, HttpStatus.OK);
    }

    @PostMapping("/addGroup/{groupId}/user/{userId}")
    public ResponseEntity<GroupUserDTO> addUserToGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        log.info("addUserToGroup : groupId = " + groupId + ", userId = " + userId);
        GroupUserDTO groupUserDTO = new GroupUserDTO();
        groupUserDTO.setIdGroup(groupId);
        groupUserDTO.setIdUsers(userId);
        GroupUserDTO newGroupUserDTO = groupUserService.saveGroupUser(groupUserDTO);
        log.info("User with id " + userId + " has been successfully added to group with id " + groupId);
        return new ResponseEntity<>(newGroupUserDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteGroup/{groupId}/user/{userId}")
    public ResponseEntity<Void> deleteUserFromGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        log.info("deleteUserFromGroup : groupId = " + groupId + ", userId = " + userId);
        groupUserService.deleteUserFromGroup(groupId, userId);
        log.info("User with id " + userId + " has been successfully removed from group with id " + groupId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}