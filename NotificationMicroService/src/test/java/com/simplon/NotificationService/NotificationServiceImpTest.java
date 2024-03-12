package com.simplon.NotificationService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceImpTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationMapper notificationMapper;

    @InjectMocks
    private NotificationServiceImp notificationService;

    @Test

    public void testCreateNotification() {
        // Mock data
        NotificationDTO notificationDTO = new NotificationDTO();
        Notification notification = new Notification();

        // Mocking mapper
        when(notificationMapper.toEntity(notificationDTO)).thenReturn(notification);
        when(notificationMapper.toDTO(notification)).thenReturn(notificationDTO);

        // Mocking repository
        when(notificationRepository.save(notification)).thenReturn(notification);

        // Test
        NotificationDTO result = notificationService.createNotification(notificationDTO);

        // Verify
        assertNotNull(result);
        verify(notificationMapper, times(1)).toEntity(notificationDTO);
        verify(notificationRepository, times(1)).save(notification);
        verify(notificationMapper, times(1)).toDTO(notification);
    }

    @Test
    public void testGetNotificationById() {
        // Mock data
        NotificationDTO notificationDTO = new NotificationDTO();
        Notification notification = new Notification();
        Long id = 1L;

        // Mocking repository
        when(notificationRepository.findById(id)).thenReturn(Optional.of(notification));

        // Mocking mapper
        when(notificationMapper.toDTO(notification)).thenReturn(notificationDTO);

        // Test
        NotificationDTO result = notificationService.getNotificationById(id);

        // Verify
        assertNotNull(result);
        verify(notificationRepository, times(1)).findById(id);
        verify(notificationMapper, times(1)).toDTO(notification);
    }

    @Test
    public void testGetAllNotifications() {
//        // Mock data
//        Notification notification1 = new Notification();
//        Notification notification2 = new Notification();
//        List<Notification> notificationList = new ArrayList<>();
//        notificationList.add(notification1);
//        notificationList.add(notification2);
//
//        // Mocking repository
//        when(notificationRepository.findAll()).thenReturn(notificationList);
//
//        // Mocking mapper
//        when(notificationMapper.toDTO(notification1)).thenReturn(new NotificationDTO());
//        when(notificationMapper.toDTO(notification2)).thenReturn(new NotificationDTO());
//
//        // Test
//        List<NotificationDTO> result = notificationService.getAllNotifications();
//
//        // Verify
//        assertEquals(2, result.size());
//        verify(notificationRepository, times(1)).findAll();
//        verify(notificationMapper, times(2)).toDTO(any(Notification.class));
//    }
    }
    @Test
    public void testMarkNotificationAsRead() {
        // Mock data
        Long notificationId = 1L;
        Notification notification = new Notification();
        notification.setIdNotif(notificationId);

        // Mocking repository
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));

        // Test
        assertDoesNotThrow(() -> notificationService.markNotificationAsRead(notificationId));

        // Verify
        verify(notificationRepository, times(1)).findById(notificationId);
        verify(notificationRepository, times(1)).save(notification);
    }



    @Test
    public void testDeleteNotificationById() {
        // Mock data
        Long id = 1L;

        // Mocking repository
        when(notificationRepository.findById(id)).thenReturn(Optional.of(new Notification()));

        // Test
        assertDoesNotThrow(() -> notificationService.deleteNotificationById(id));

        // Verify
        verify(notificationRepository, times(1)).findById(id);
        verify(notificationRepository, times(1)).deleteById(id);
    }

    @Test
    public void testGetNotificationsByRecipientId() {
        // Mock data
        Long recipientId = 1L;
        List<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification());

        // Mocking repository
        when(notificationRepository.findByRecipientId(recipientId)).thenReturn(notifications);

        // Mocking mapper
        when(notificationMapper.toDTO(any(Notification.class))).thenReturn(new NotificationDTO());

        // Test
        List<NotificationDTO> result = notificationService.getNotificationsByRecipientId(recipientId);

        // Verify
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(notificationRepository, times(1)).findByRecipientId(recipientId);
        verify(notificationMapper, times(1)).toDTO(any(Notification.class));
    }

    @Test
    public void testDeleteAllNotifications() {
        // Test
        assertDoesNotThrow(() -> notificationService.deleteAllNotifications());

        // Verify
        verify(notificationRepository, times(1)).deleteAll();
    }
}
