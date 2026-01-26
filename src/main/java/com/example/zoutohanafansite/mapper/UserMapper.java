package com.example.zoutohanafansite.mapper;

import com.example.zoutohanafansite.entity.auth.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE login_id = #{loginId} AND deleted = false")
    User getUserByLoginId(String loginId);

    @Insert("""
            INSERT INTO users 
                (login_id, nickname, password, self_introduction,
                 icon, address, birth_year, gender, security_key, status)
            VALUES (#{loginId}, #{nickname}, #{password}, #{selfIntroduction}, #{icon}, #{address}, #{birthYear}, #{gender}, #{securityKey}, #{status})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);

    @Update("UPDATE users SET password = #{password}, security_key = #{securityKey} WHERE login_id = #{loginId}")
    void updatePassword(String password, String securityKey, String loginId);

    @Update("UPDATE users SET deleted = true WHERE id = #{id}")
    void deleteUser(long id);


}
