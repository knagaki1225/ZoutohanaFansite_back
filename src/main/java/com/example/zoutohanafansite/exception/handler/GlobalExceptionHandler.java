package com.example.zoutohanafansite.exception.handler;

import com.example.zoutohanafansite.entity.auth.ErrorStatus;
import com.example.zoutohanafansite.exception.AccessDeniedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        ErrorStatus errorStatus = new ErrorStatus("FORBIDDEN", "アクセスする権限がありません");
        redirectAttributes.addFlashAttribute("errorStatus", errorStatus);
        return "redirect:/error-4xx";
    }
}
