package com.example.zoutohanafansite.repository;

import com.example.zoutohanafansite.entity.auth.User;
import com.example.zoutohanafansite.mapper.UserMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final UserMapper userMapper;

    public UserRepository(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User getUserByLoginId(String loginId){
        return userMapper.getUserByLoginId(loginId);
    }

    public void insertUser(User user){
        userMapper.insertUser(user);
    }

    public void updatePassword(String password, String securityKey, String loginId){
        userMapper.updatePassword(password,securityKey,loginId);
    }

    public void deleteUser(long id){
        userMapper.deleteUser(id);
    }
}
