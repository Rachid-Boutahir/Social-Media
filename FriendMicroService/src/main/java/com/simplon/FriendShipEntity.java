package com.simplon;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "friend_ship")
public class FriendShipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friendship_id", nullable = false)
    private long friendshipId;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "friend_id", nullable = false)
    private long friendId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private StatusFriendEnum status;

    @Column(name = "date_addition")
    private LocalDate dateAddition;

    @Column(name = "is_deleted")
    private boolean deleted;

    @Column(name = "date_delete")
    private LocalDate dateDelete;

}
