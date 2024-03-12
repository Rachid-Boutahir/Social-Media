package com.simplon.group;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("GROUP")
public interface GroupClient {
    @GetMapping("/api/group/all")
    ResponseEntity<List<GroupDTO>> getAllGroups();

    @GetMapping("/api/group/{id}")
    ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id);

    @PostMapping("/api/group/create")
    ResponseEntity<GroupDTO> createGroup(@RequestBody GroupDTO groupDTO);

    @PutMapping("/api/group/update/{id}")
    ResponseEntity<GroupDTO> updateGroup(@PathVariable Long id, @RequestBody GroupDTO groupDTO);

    @DeleteMapping("/api/group/delete/{id}")
    ResponseEntity<Void> deleteGroup(@PathVariable Long id);

    @GetMapping("/api/groupUser/group/{groupId}/users")
    ResponseEntity<List<GroupUserDTO>> getAllUsersInGroup(@PathVariable Long groupId);

    @GetMapping("/api/groupUser/user/{userId}/groups")
    ResponseEntity<List<GroupUserDTO>> getAllGroupsForUser(@PathVariable Long userId);

    @PostMapping("/api/groupUser/addGroup/{groupId}/user/{userId}")
    ResponseEntity<GroupUserDTO> addUserToGroup(@PathVariable Long groupId, @PathVariable Long userId);

    @DeleteMapping("/api/groupUser/deleteGroup/{groupId}/user/{userId}")
    ResponseEntity<Void> deleteUserFromGroup(@PathVariable Long groupId, @PathVariable Long userId);
}