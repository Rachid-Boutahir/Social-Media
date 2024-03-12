package com.simplon.NotificationService;

import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class NotificationServiceImp implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private static final  Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImp.class);


    @Override
    public NotificationDTO createNotification(NotificationDTO notificationDTO) {
        try {
            Notification notification = notificationMapper.toEntity(notificationDTO);
            notificationRepository.save(notification);
            return notificationMapper.toDTO(notification);
        } catch (Exception e) {
            LOGGER.error("An error occurred while creating the notification: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public NotificationDTO getNotificationById(Long id) {
        try {
            Optional<Notification> notificationOptional = notificationRepository.findById(id);
            if (notificationOptional.isPresent()) {
                return notificationMapper.toDTO(notificationOptional.get());
            } else {
                LOGGER.warn("Notification with ID {} not found", id);
                return null;
            }
        } catch (Exception e) {
            LOGGER.error("An error occurred while fetching notification with ID {}: {}", id, e.getMessage());
            return null;
        }
    }

    @Override
    public Page<NotificationDTO> getAllNotifications(Pageable pageable) {
        try {
            return notificationRepository.findAll(pageable)
                    .map(notificationMapper::toDTO);
        } catch (Exception e) {
            LOGGER.error("An error occurred while fetching all notifications: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch notifications", e);
        }
    }
    @Override
    public List<NotificationDTO> getUnreadNotifications() {
        try {
            return notificationRepository.findByReadNotifFalse().stream()
                    .map(notificationMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error("An error occurred while fetching all notifications: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
    @Override
    public void markNotificationAsRead(Long id) {
        try {
            Optional<Notification> notificationOptional = notificationRepository.findById(id);

            if (notificationOptional.isPresent()) {
                Notification notification = notificationOptional.get();
                notification.setReadNotif(Boolean.TRUE);
                notificationRepository.save(notification);

            } else {
                LOGGER.warn("Notification with ID {} not found", id);
            }
        } catch (Exception e) {
            LOGGER.error("An error occurred while updating the notification: {}", e.getMessage());
        }
    }


    @Override
    public void deleteNotificationById(Long id) {
        try {
            Optional<Notification> notificationOptional = notificationRepository.findById(id);
            if (notificationOptional.isPresent()) {
                notificationRepository.deleteById(id);
            } else {
                LOGGER.warn("Notification with ID {} not found", id);
            }
        } catch (Exception e) {
            LOGGER.error("An error occurred while deleting the notification: {}", e.getMessage());
        }
    }

    @Override
    public List<NotificationDTO> getNotificationsByRecipientId(Long recipientId) {
        try {

            List<Notification> notifications = notificationRepository.findByRecipientId(recipientId);
            return notifications.stream()
                    .map(notificationMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error("An error occurred while fetching notifications for recipient with ID {}: {}", recipientId, e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public void deleteAllNotifications() {
        try {
            notificationRepository.deleteAll();
            LOGGER.info("All notifications deleted successfully.");
        } catch (Exception e) {
            LOGGER.error("An error occurred while deleting all notifications: {}", e.getMessage());
        }
    }


}
