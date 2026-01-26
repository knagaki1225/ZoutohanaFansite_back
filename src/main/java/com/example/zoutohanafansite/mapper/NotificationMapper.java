package com.example.zoutohanafansite.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NotificationMapper {
    @Select("""
            SELECT COUNT(*)
            FROM notifications
            WHERE user_id = #{userId}        -- 取得したいユーザーのIDを指定
              AND seen = false           -- 未読状態
              AND deleted = false;       -- 削除されていない
            """)
    int selectSeenNum(long userId);
}
