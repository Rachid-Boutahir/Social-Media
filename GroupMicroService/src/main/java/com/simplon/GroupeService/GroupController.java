package com.simplon.GroupeService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/api/group")
public class GroupController {

    @Autowired
    private IServiceGroup groupService;

    @GetMapping("/all")
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        log.info("Getting all groups");
        List<GroupDTO> groupDTOs = groupService.getAllGroups();
        log.info("Retrieved " + groupDTOs.size() + " groups");
        return new ResponseEntity<>(groupDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id) {
        log.info("Getting group with id " + id);
        GroupDTO groupDTO = groupService.getGroupById(id);
        log.info("Retrieved group with id " + id);
        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<GroupDTO> createGroup(@RequestBody GroupDTO groupDTO) {
        log.info("Creating group with GroupDTO: " + groupDTO);
        GroupDTO newGroupDTO = groupService.saveGroup(groupDTO);
        log.info("Created group with id " + newGroupDTO.getIdGroup());
        return new ResponseEntity<>(newGroupDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GroupDTO> updateGroup(@PathVariable Long id, @RequestBody GroupDTO groupDTO) {
        log.info("Updating group with id " + id);
        groupDTO.setIdGroup(id);
        GroupDTO updatedGroupDTO = groupService.updateGroup(groupDTO);
        log.info("Updated group with id " + updatedGroupDTO.getIdGroup());
        return new ResponseEntity<>(updatedGroupDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        log.info("Deleting group with id " + id);
        groupService.deleteGroup(id);
        log.info("Deleted group with id " + id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}