package com.example.zoutohanafansite.entity.enums;

import java.util.Arrays;

public enum ProjectStatus {
    BEFORE_SUBMISSION("BEFORE_SUBMISSION", "書評投稿期間前"),
    DURING_SUBMISSION("DURING_SUBMISSION", "書評投稿期間中"),
    FIRST_PHASE("FIRST_PHASE", "一次審査中"),
    SECOND_PHASE_VOTING("SECOND_PHASE_VOTING", "二次審査(投票)中"),
    SECOND_PHASE_VERIFY("SECOND_PHASE_VERIFY", "二次審査(確認)中"),
    SECOND_PHASE_RESULT("SHOW_NOMINATED", "二次審査結果発表"),
    AWARD_ANNOUNCEMENT("AWARD_ANNOUNCEMENT", "大賞発表");


    private final String dbValue;
    private final String label;

    ProjectStatus(String dbValue, String label) {
        this.dbValue = dbValue;
        this.label = label;
    }

    public String getDbValue() {
        return dbValue;
    }

    public String getLabel() {
        return label;
    }

    //    データベースの文字列から Enum を取得
    public static ProjectStatus fromString(String dbValue) {
        return Arrays.stream(ProjectStatus.values())
                .filter(e -> e.dbValue.equals(dbValue))
                .findFirst()
                .orElse(null);
    }
}
