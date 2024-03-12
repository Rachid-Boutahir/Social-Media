package com.simplon.NotificationService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private static final Logger LOGGER = LogManager.getLogger(NotificationController.class);

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @GetMapping
    public ResponseEntity<Page<NotificationDTO>> getAllNotifications(@RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size) {
        try {
            Sort sort = Sort.by("dateNotif").descending();
            Pageable pageable = PageRequest.of(page, size, sort);

            Page<NotificationDTO> notificationsPage = notificationService.getAllNotifications(pageable);

            if (!notificationsPage.isEmpty()) {
                return new ResponseEntity<>(notificationsPage, HttpStatus.OK);
            } else {
                LOGGER.warn("No notifications found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOGGER.error("An error occurred while fetching all notifications", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/unread")
    public ResponseEntity<List<NotificationDTO>> getUnreadNotifications() {
        try {
            List<NotificationDTO> notifications = notificationService.getUnreadNotifications();
            if (!notifications.isEmpty()) {
                return new ResponseEntity<>(notifications, HttpStatus.OK);
            } else {
                LOGGER.warn("All notifications are read");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOGGER.error("An error occurred while fetching all notifications", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notificationDTO) {
        try {
            NotificationDTO createdNotification = notificationService.createNotification(notificationDTO);
            if (createdNotification != null) {
                return new ResponseEntity<>(createdNotification, HttpStatus.CREATED);
            } else {
                LOGGER.error("Failed to create notification");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            LOGGER.error("An error occurred while creating the notification", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CircuitBreaker(name ="getNotification", fallbackMethod = "getDefaultNotification")
    @GetMapping("/{id}")
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable Long id) {
        try {
            NotificationDTO notificationDTO = notificationService.getNotificationById(id);
            if (notificationDTO != null) {
                return new ResponseEntity<>(notificationDTO, HttpStatus.OK);
            } else {
                LOGGER.warn("Notification with ID {} not found", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOGGER.error("An error occurred while fetching notification with ID " + id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/recipient/{recipientId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByRecipientId(@PathVariable Long recipientId) {
        try {
            List<NotificationDTO> notifications = notificationService.getNotificationsByRecipientId(recipientId);
            if (!notifications.isEmpty()) {
                return new ResponseEntity<>(notifications, HttpStatus.OK);
            } else {
                LOGGER.warn("No notifications found for recipient with ID {}", recipientId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOGGER.error("An error occurred while fetching notifications for recipient with ID " + recipientId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificationById(@PathVariable Long id) {
        try {
            notificationService.deleteNotificationById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOGGER.error("An error occurred while deleting the notification with ID " + id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllNotifications() {
        try {
            notificationService.deleteAllNotifications();
            LOGGER.info("All notifications deleted successfully.");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOGGER.error("An error occurred while deleting all notifications", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/markAsRead/{id}")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable Long id) {
        try {
            notificationService.markNotificationAsRead(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOGGER.error("An error occurred while marking the notification as read", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity getDefaultNotification(Long id, Throwable throwable) {
        return ResponseEntity.ok().body(new NotificationDTO(
                id,
                "Une erreur s'est produite : " + throwable.getMessage(),
                "Erreur",
                false,
                null,
                null,
                LocalDateTime.now()
        ));
    }

//    public NotificationDTO getDefaultNotifications(Exception e){
//        return new NotificationDTO(
//                null,
//                "Une erreur s'est produite : " + e.getMessage(),
//                "Erreur",
//                false,
//                null,
//                null,
//                LocalDateTime.now()
//        );
//    }
}
