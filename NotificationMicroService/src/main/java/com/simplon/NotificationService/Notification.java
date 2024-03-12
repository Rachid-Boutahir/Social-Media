package com.simplon.NotificationService;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotif;
    private String contentNotif;
    private String typeNotif;
    private boolean readNotif ;
    private Long senderId;
    private Long recipientId;
    private LocalDateTime dateNotif;
}
