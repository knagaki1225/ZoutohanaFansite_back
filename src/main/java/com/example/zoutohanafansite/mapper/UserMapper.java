package com.example.zoutohanafansite.mapper;

import com.example.zoutohanafansite.entity.auth.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE login_id = #{loginId} AND deleted = false")
    User getUserByLoginId(String loginId);

    @Select("""
        <script>
        SELECT
            u.*,
            COUNT(r.id) AS review_count
        FROM users u
        LEFT JOIN reviews r
            ON r.user_id = u.id
            AND r.deleted = false
            AND r.draft = false
        WHERE u.deleted = false
        <if test="keyword != null and keyword != ''">
            AND (
                u.login_id LIKE CONCAT('%', #{keyword}, '%')
                OR u.nickname LIKE CONCAT('%', #{keyword}, '%')
                OR u.address LIKE CONCAT('%', #{keyword}, '%')
            )
        </if>
        GROUP BY u.id
        ORDER BY
            CASE WHEN #{sort} = 'created_desc' THEN u.created_at END DESC,
            CASE WHEN #{sort} = 'created_asc' THEN u.created_at END ASC,
            CASE WHEN #{sort} = 'birthYear_desc' THEN u.birth_year END DESC,
            CASE WHEN #{sort} = 'birthYear_asc' THEN u.birth_year END ASC,
            CASE WHEN #{sort} = 'reviewCount_desc' THEN COUNT(r.id) END DESC,
            CASE WHEN #{sort} = 'reviewCount_asc' THEN COUNT(r.id) END ASC
        </script>                                                            
        """)
    List<User> getAllUsers(String sort, String keyword);

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
