package com.example.zoutohanafansite.controller;

import com.example.zoutohanafansite.entity.admin.project.AdminDashProject;
import com.example.zoutohanafansite.entity.admin.project.AdminProjectCard;
import com.example.zoutohanafansite.entity.auth.User;
import com.example.zoutohanafansite.entity.project.Project;
import com.example.zoutohanafansite.service.ProjectService;
import com.example.zoutohanafansite.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProjectService projectService;
    private final UserService userService;

    public AdminController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
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
    public String accountList(@RequestParam(required = false, defaultValue="created_desc") String sort,
                              @RequestParam(required = false) String keyword,
                              Model model){
        List<User> users = userService.getAllUsers(sort, keyword);
        model.addAttribute("users", users);
        model.addAttribute("sort", sort);
        model.addAttribute("keyword", keyword);

        return "admin/account_list";
    }

    @GetMapping("/account/view")
    public String accountView(@RequestParam String loginId, Model model) {
        User user = userService.getUserById(loginId);
        model.addAttribute("user", user);
        return "admin/account_view";
    }
}
