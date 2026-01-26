package com.example.zoutohanafansite.mapper;

import com.example.zoutohanafansite.entity.auth.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminUserMapper {
    @Select("""
        SELECT * FROM admin_users
        WHERE login_id = #{login_id}
    """)
    AdminUser getAdminUserByLoginId(String loginId);
}
