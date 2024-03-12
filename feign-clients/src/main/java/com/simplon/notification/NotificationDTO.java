package com.simplon.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class NotificationDTO {
    private Long idNotif;
    private String contentNotif;
    private String typeNotif;
    private boolean readNotif;
    private Long senderId;
    private Long recipientId;
    private LocalDateTime dateNotif;

}
