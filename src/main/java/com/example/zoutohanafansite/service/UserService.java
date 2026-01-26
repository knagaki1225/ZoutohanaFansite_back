package com.example.zoutohanafansite.service;

import com.example.zoutohanafansite.entity.auth.User;
import com.example.zoutohanafansite.entity.form.NewPasswordForm;
import com.example.zoutohanafansite.entity.form.PasswordResetForm;
import com.example.zoutohanafansite.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GenerateSecurityKeyService generateSecurityKeyService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, GenerateSecurityKeyService generateSecurityKeyService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.generateSecurityKeyService = generateSecurityKeyService;
    }

    public void insertUser(User user){
        userRepository.insertUser(user);
    }

    public User getUserById(String loginId){
        return userRepository.getUserByLoginId(loginId);
    }

    public boolean checkSecurityKey(PasswordResetForm passwordResetForm){
        User user = getUserById(passwordResetForm.getLoginId());
        return passwordEncoder.matches(passwordResetForm.getSecurityKey(), user.getSecurityKey());
    }

    public void updatePassword(String password, String securityKey, String loginId){
        userRepository.updatePassword(password,securityKey,loginId);
    }

    public String passwordReset(NewPasswordForm  newPasswordForm){
        if(newPasswordForm.getPassword().equals(newPasswordForm.getConfirmPassword())){
            String hashedPassword = passwordEncoder.encode(newPasswordForm.getPassword());
            String newSecurityKey = GenerateSecurityKeyService.generateSecurityKey();
            String hashedSecurityKey = passwordEncoder.encode(newSecurityKey);
            updatePassword(hashedPassword, hashedSecurityKey, newPasswordForm.getLoginId());
            return newSecurityKey;
        }
        return null;
    }

    public void deleteUser(long id){
        userRepository.deleteUser(id);
    }


}
