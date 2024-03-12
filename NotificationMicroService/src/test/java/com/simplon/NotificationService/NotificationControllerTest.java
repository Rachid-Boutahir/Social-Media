package com.simplon.NotificationService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@WebMvcTest(NotificationController.class)
class NotificationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;
    @Test
    void getAllNotifications() {
    }

    @Test
    void getUnreadNotifications() {
    }

    @Test
    void createNotification() {
    }

    @Test
    void getNotificationById() {
    }

    @Test
    void getNotificationsByRecipientId() {
    }

    @Test
    void deleteNotificationById() {
    }

    @Test
    void deleteAllNotifications() {
    }

    @Test
    void markNotificationAsRead() {
    }
}