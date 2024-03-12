package com.simplon.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("NOTIFICATION")
public interface NotificationClient {

    @GetMapping("/notifications")
    ResponseEntity<Page<NotificationDTO>> getAllNotifications(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size);

    @GetMapping("/notifications/unread")
    ResponseEntity<List<NotificationDTO>> getUnreadNotifications();

    @PostMapping("/notifications")
    ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notificationDTO);

    @GetMapping("/notifications/{id}")
    ResponseEntity<NotificationDTO> getNotificationById(@PathVariable Long id);

    @GetMapping("/notifications/recipient/{recipientId}")
    ResponseEntity<List<NotificationDTO>> getNotificationsByRecipientId(@PathVariable Long recipientId);

    @DeleteMapping("/notifications/{id}")
    ResponseEntity<Void> deleteNotificationById(@PathVariable Long id);

    @DeleteMapping("/notifications/deleteAll")
    ResponseEntity<Void> deleteAllNotifications();

    @PutMapping("/notifications/markAsRead/{id}")
    ResponseEntity<Void> markNotificationAsRead(@PathVariable Long id);
}
