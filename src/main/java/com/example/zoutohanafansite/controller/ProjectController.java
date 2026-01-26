package com.example.zoutohanafansite.controller;

import com.example.zoutohanafansite.entity.project.Project;
import com.example.zoutohanafansite.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/{urlKey}")
    public String project(@PathVariable("urlKey") String urlKey, Model model) {
        Project project = projectService.getProjectByUrlKey(urlKey);
        if (project == null) {
            // 不正アクセス
        }

        String returnUrl = "project/first-ex";

        switch (project.getStatus()){
            case BEFORE_SUBMISSION :
                break;
            case DURING_SUBMISSION :
                break;
            case FIRST_PHASE:
                break;
            case SECOND_PHASE_VOTING:
                returnUrl = "project/first-review";
                break;
            case SECOND_PHASE_VERIFY:
                break;
            case SECOND_PHASE_RESULT:
                break;
            case AWARD_ANNOUNCEMENT:
                break;
        }

        model.addAttribute("project", project);

        return returnUrl;
    }
}
