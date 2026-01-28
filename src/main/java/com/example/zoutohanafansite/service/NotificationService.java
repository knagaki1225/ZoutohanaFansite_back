package com.example.zoutohanafansite.service;

import com.example.zoutohanafansite.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /**
     * 未読のメール件数取得
     *
     * @param id userId
     * @return int
     */
    public int getSeenNum(long id){
        return notificationRepository.selectSeenNum(id);
    }
}
