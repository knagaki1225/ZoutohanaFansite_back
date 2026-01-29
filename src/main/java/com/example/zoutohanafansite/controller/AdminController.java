package com.example.zoutohanafansite.controller;

import com.example.zoutohanafansite.entity.admin.project.AdminDashProject;
import com.example.zoutohanafansite.entity.admin.project.AdminProjectCard;
import com.example.zoutohanafansite.entity.project.Project;
import com.example.zoutohanafansite.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProjectService projectService;

    public AdminController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/dash")
    public String admin(Model model){
//        List<Project> projects = projectService.getAllOngoingProjects();
//        List<AdminDashProject> adminDashProjects = new ArrayList<>();
//        for(Project project : projects){
//            adminDashProjects.add(new AdminDashProject(project));
//        }
//        model.addAttribute("projects",adminDashProjects);

        List<AdminProjectCard> projects = projectService.getAllOngoingProjectsAdmin();
        model.addAttribute("projects", projects);

        return "admin/top";
    }

    @GetMapping("/account/list")
    public String accountList(Model model){
        return "admin/account_list";
    }
}
