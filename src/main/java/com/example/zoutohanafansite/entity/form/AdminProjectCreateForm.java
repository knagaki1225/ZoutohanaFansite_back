package com.example.zoutohanafansite.entity.form;

import com.example.zoutohanafansite.entity.enums.ThemeColor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminProjectCreateForm {
    private String name;
    private String urlKey;
    private String introduction;
    private ThemeColor themeColor;
    private LocalDateTime projectStartAt;
    private LocalDateTime projectEndAt;
    private LocalDateTime submissionStartAt;
    private LocalDateTime submissionEndAt;
    private LocalDateTime votingStartAt;
    private LocalDateTime votingEndAt;
}