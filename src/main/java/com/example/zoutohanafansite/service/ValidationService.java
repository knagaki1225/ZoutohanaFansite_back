package com.example.zoutohanafansite.service;

import com.example.zoutohanafansite.entity.form.SignupForm;
import com.example.zoutohanafansite.entity.validator.SignupValidator;
import com.example.zoutohanafansite.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {
    private final UserRepository userRepository;

    public ValidationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    private boolean isNull(Object o){
        return o == null;
    }

    public SignupValidator validateSignUp(SignupForm signupForm) {
        SignupValidator signupValidator = new SignupValidator();

        if(signupForm.getLoginId().isEmpty()){
            signupValidator.setNullLoginId(true);
        }else{
            if(userRepository.getUserByLoginId(signupForm.getLoginId()) != null){
                signupValidator.setExistsLoginId(true);
            }
        }
        signupValidator.setNullNickName(signupForm.getNickname().isEmpty());
        signupValidator.setNullAddress(signupForm.getAddress().isEmpty());
        signupValidator.setNullBirthYear(isNull(signupForm.getBirthYear()));
        signupValidator.setNullPassword(signupForm.getPassword().isEmpty());
        signupValidator.setNullConfirmPassword(signupForm.getConfirmPassword().isEmpty());

        if(!signupForm.getPassword().equals(signupForm.getConfirmPassword())){
            signupValidator.setPasswordMismatch(true);
        }

        signupValidator.updateStatus();

        return signupValidator;
    }
}
