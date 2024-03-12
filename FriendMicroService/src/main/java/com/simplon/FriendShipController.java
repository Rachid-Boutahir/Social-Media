package com.simplon;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/friend-ships")
public class FriendShipController {

    private final FriendShipService friendShipService;

    @GetMapping("/requested-accepted-rejected/all")
    public ResponseEntity<List<FriendShipDto>> getAllFriendRequestedAcceptedRejected() {
        log.info("new get request for retrieve data from FriendShip database methode invoked getAllFriendRequestedAcceptedRejected()  ");
        return ResponseEntity.ok(friendShipService.getAllFriendRequestedAcceptedRejected());
    }

    @GetMapping("/accepted")
    public ResponseEntity<List<FriendShipDto>> getAllFriendAccepted() {
        log.info("new get request for retrieve data from FriendShip database methode invoked getAllFriendAccepted()  ");
        return ResponseEntity.ok(friendShipService.getAllFriendAccepted());
    }

    @GetMapping("/requested")
    public ResponseEntity<List<FriendShipDto>> getAllFriendRequested() {
        log.info("new get request for retrieve data from FriendShip database methode invoked getAllFriendRequested()  ");
        return ResponseEntity.ok(friendShipService.getAllFriendRequested());
    }

    @GetMapping("/rejected")
    public ResponseEntity<List<FriendShipDto>> getAllFriendRejected() {
        log.info("new get request for retrieve data from FriendShip database table methode invoked getAllFriendRejected() ");
        return ResponseEntity.ok(friendShipService.getAllFriendRejected());
    }

    @PostMapping("/send-request")
    public ResponseEntity<FriendShipDto> addFriendShip(@RequestBody FriendShipDto friendShipDto) {
        log.info("new request accepted {}", friendShipDto.toString());

        FriendShipDto addfriendShipDto = friendShipService.saveNewFriend(friendShipDto);
        return new ResponseEntity<>(addfriendShipDto, HttpStatus.CREATED);
    }

    @PutMapping("/update-friend/{friendId}")
    public ResponseEntity<FriendShipDto> updateFriendShip(
            @PathVariable("friendId") long friendId,
            @RequestBody FriendShipDto friendShipDto) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(friendShipService.updateFriendShip(friendId, friendShipDto));
    }


    @DeleteMapping("/delete-friend")
    public ResponseEntity<String> deleteFriendByUserIdAndFriendId(
            @RequestParam("userId") Long userId,
            @RequestParam("friendId") Long friendId) throws ChangeSetPersister.NotFoundException {
        friendShipService.deleteFriendByUserIdAndFriendId(userId, friendId);
        log.info("new delete request is send for friendid :{}, ", friendId);
        return ResponseEntity.ok("Friend with id:" + friendId + " has been deleted successfully!");
    }


    @GetMapping("/search-Specific-friend")
    public ResponseEntity<FriendShipDto> searchSpecificFriend(
            @RequestParam("userId") long userId,
            @RequestParam("friendId") long friendId,
            @RequestParam("status") StatusFriendEnum status) throws ChangeSetPersister.NotFoundException {

        log.info("Friend with id:" + friendId + " has been found!");
        return ResponseEntity.ok(friendShipService.searchSpecificFriend(userId, friendId, status));
    }


}
