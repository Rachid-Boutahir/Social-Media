package com.simplon.NotificationService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {
    NotificationDTO createNotification(NotificationDTO notificationDTO);
    NotificationDTO getNotificationById(Long id);
    Page<NotificationDTO> getAllNotifications(Pageable pageable);
    void markNotificationAsRead(Long id);
    void deleteNotificationById(Long id);
    List<NotificationDTO> getNotificationsByRecipientId(Long recipientId);
    void deleteAllNotifications();
    List<NotificationDTO> getUnreadNotifications();


}
