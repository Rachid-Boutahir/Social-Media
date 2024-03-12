package com.simplon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FriendShipDto implements Serializable {

    private long friendshipId;
    private long userId;
    private long friendId;
    private StatusFriendEnum status;
    private boolean deleted;
    private LocalDate dateAddition;
    private LocalDate dateDelete;

}
