package com.simplon;

import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Set;

public interface FriendShipService {

    List<FriendShipDto> getAllFriendRequestedAcceptedRejected();

    List<FriendShipDto> getAllFriendAccepted();

    List<FriendShipDto> getAllFriendRequested();

    List<FriendShipDto> getAllFriendRejected();

    FriendShipDto saveNewFriend(FriendShipDto friendShipDto);

    FriendShipDto updateFriendShip(long friendId, FriendShipDto friendShipDto) throws ChangeSetPersister.NotFoundException;

    void deleteFriendByUserIdAndFriendId(long userId,long friendId) throws ChangeSetPersister.NotFoundException;

    FriendShipDto searchSpecificFriend(long userId, long friendId, StatusFriendEnum status) throws ChangeSetPersister.NotFoundException;


}
