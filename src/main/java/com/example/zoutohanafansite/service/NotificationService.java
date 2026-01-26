package com.example.zoutohanafansite.service;

import com.example.zoutohanafansite.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public int getSeenNum(long userId){
        return notificationRepository.selectSeenNum(userId);
    }
}
