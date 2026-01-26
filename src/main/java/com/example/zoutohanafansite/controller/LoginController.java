package com.example.zoutohanafansite.controller;

import com.example.zoutohanafansite.entity.auth.User;
import com.example.zoutohanafansite.entity.form.NewPasswordForm;
import com.example.zoutohanafansite.entity.form.PasswordResetForm;
import com.example.zoutohanafansite.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/password-reset")
    public String passwordReset(Model model, @RequestParam long id) {
        PasswordResetForm passwordResetForm = new PasswordResetForm();
        model.addAttribute("passwordResetForm", passwordResetForm);
        model.addAttribute("id", id);
        return "/auth/pw-reset";
    }

    @PostMapping("/password-reset")
    public String passwordReset(@RequestParam long id, PasswordResetForm passwordResetForm, RedirectAttributes redirectAttributes){
        if(userService.checkSecurityKey(passwordResetForm)){
            redirectAttributes.addFlashAttribute("alreadyCheck", true);
            User user = userService.getUserById(passwordResetForm.getLoginId());
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/password-reset/input-password?id="+id;
        }
        redirectAttributes.addFlashAttribute("error", true);
        return "redirect:/password-reset?id="+id;
    }

    @GetMapping("/password-reset/input-password")
    public String inputResetPassword(@RequestParam long id, @ModelAttribute User user, Model model){
        if(user == null){
            // エラー
        }
        NewPasswordForm newPasswordForm = new NewPasswordForm();
        newPasswordForm.setLoginId(user.getLoginId());

        model.addAttribute("newPasswordForm", newPasswordForm);
        model.addAttribute("id", id);

        return "/auth/pw-set";
    }

    @PostMapping("/password-reset/input-password")
    public String updatePassword(@RequestParam long id, NewPasswordForm newPasswordForm, RedirectAttributes redirectAttributes){
        String newSecurityKey = userService.passwordReset(newPasswordForm);
        if(newSecurityKey == null){
            redirectAttributes.addFlashAttribute("error", true);
            return "redirect:/password-reset/input-password?id="+id;
        }

        redirectAttributes.addFlashAttribute("id", id);
        redirectAttributes.addFlashAttribute("securityKey", newSecurityKey);
        return "redirect:/password-reset/security-key?id="+id;
    }

    @GetMapping("/password-reset/security-key")
    public String securityKey(@RequestParam long id, Model model,@ModelAttribute String securityKey){
        model.addAttribute("id", id);
        return "auth/pw-reset-confirm";
    }

    @GetMapping("/admin/login")
    public String adminLogin(){
        return "admin/login";
    }
}
