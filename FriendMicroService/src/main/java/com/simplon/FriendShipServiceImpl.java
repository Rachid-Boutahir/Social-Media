package com.simplon;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class FriendShipServiceImpl implements FriendShipService {

    private final FriendShipRepository friendShipRepository;

    private final ModelMapper modelMapper;


    @Override
    public List<FriendShipDto> getAllFriendRequestedAcceptedRejected() {
        List<FriendShipEntity> friendShips = friendShipRepository.findByDeletedFalse();
        return friendShips.stream().map(friendShip -> modelMapper.map(friendShip, FriendShipDto.class)).toList();
    }

    @Override
    public List<FriendShipDto> getAllFriendAccepted() {
        List<FriendShipEntity> friendShips = friendShipRepository.findByDeletedFalseAndStatus(StatusFriendEnum.ACCEPTED);
        return friendShips.stream().map(friendShip -> modelMapper.map(friendShip, FriendShipDto.class)).toList();
    }

    @Override
    public List<FriendShipDto> getAllFriendRequested() {
        List<FriendShipEntity> friendShips = friendShipRepository.findByDeletedFalseAndStatus(StatusFriendEnum.REQUESTED);
        return friendShips.stream().map(friendShip -> modelMapper.map(friendShip, FriendShipDto.class)).toList();
    }

    @Override
    public List<FriendShipDto> getAllFriendRejected() {
        List<FriendShipEntity> friendShips = friendShipRepository.findByDeletedFalseAndStatus(StatusFriendEnum.REJECTED);
        return friendShips.stream().map(friendShip -> modelMapper.map(friendShip, FriendShipDto.class)).toList();
    }

    @Override
    public FriendShipDto saveNewFriend(@NotNull FriendShipDto friendShipDto) {

        friendShipDto.setDateAddition(LocalDate.now());
        FriendShipEntity friendShipEntity = friendShipRepository.save(modelMapper.map(friendShipDto, FriendShipEntity.class));
        return modelMapper.map(friendShipEntity, FriendShipDto.class);
    }

    @Override
    public FriendShipDto updateFriendShip(long friendId, @NotNull FriendShipDto friendShipDto) throws ChangeSetPersister.NotFoundException {

        FriendShipEntity existingfriendShipEntity = friendShipRepository
                .findByFriendshipIdAndFriendIdAndDeletedFalseAndStatusAllIgnoreCase(friendShipDto.getFriendshipId(), friendId, StatusFriendEnum.REQUESTED)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        existingfriendShipEntity.setStatus(friendShipDto.getStatus());

        FriendShipEntity updatedFriendShip = friendShipRepository.save(existingfriendShipEntity);
        updatedFriendShip.setFriendId(friendId);

        return modelMapper.map(updatedFriendShip, FriendShipDto.class);

    }


    @Override
    public void deleteFriendByUserIdAndFriendId(long userId, long friendId) throws ChangeSetPersister.NotFoundException {
        FriendShipEntity friendShip = friendShipRepository
                .findByUserIdAndFriendIdAndDeletedFalse(userId, friendId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        friendShip.setStatus(StatusFriendEnum.REJECTED);
        friendShip.setDeleted(true);
        friendShip.setDateDelete(LocalDate.now());
        friendShipRepository.save(friendShip);
    }


    @Override
    public FriendShipDto searchSpecificFriend(long userId, long friendId, StatusFriendEnum status) throws ChangeSetPersister.NotFoundException {
        FriendShipEntity searchingFriendEntity = friendShipRepository
                .findByUserIdAndFriendIdAndStatusAndDeletedFalseAllIgnoreCase(userId, friendId, status)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        return modelMapper.map(searchingFriendEntity, FriendShipDto.class);
    }


}
