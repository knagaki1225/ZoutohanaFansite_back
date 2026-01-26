package com.example.zoutohanafansite.repository;

import com.example.zoutohanafansite.mapper.NotificationMapper;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationRepository {
    private final NotificationMapper notificationMapper;

    public NotificationRepository(NotificationMapper notificationMapper) {
        this.notificationMapper = notificationMapper;
    }

    public int selectSeenNum(long userId){
        return notificationMapper.selectSeenNum(userId);
    }
}
