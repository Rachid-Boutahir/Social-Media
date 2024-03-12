package com.simplon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FriendShipRepository extends JpaRepository<FriendShipEntity, Long> {

    Optional<FriendShipEntity> findByFriendshipIdAndFriendIdAndDeletedFalseAndStatusAllIgnoreCase(long friendshipId, long friendId, StatusFriendEnum status);

    List<FriendShipEntity> findByDeletedFalse();

    Optional<FriendShipEntity> findByUserIdAndFriendIdAndDeletedFalse(long userId, long friendId);

    List<FriendShipEntity> findByDeletedFalseAndStatus(StatusFriendEnum status);

    Optional<FriendShipEntity> findByUserIdAndFriendIdAndStatusAndDeletedFalseAllIgnoreCase(long userId, long friendId, StatusFriendEnum status);


}

