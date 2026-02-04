package com.example.zoutohanafansite.controller;

import com.example.zoutohanafansite.entity.admin.project.AdminDashProject;
import com.example.zoutohanafansite.entity.admin.project.AdminProjectCard;
import com.example.zoutohanafansite.entity.auth.User;
import com.example.zoutohanafansite.entity.enums.ProjectStatus;
import com.example.zoutohanafansite.entity.form.ProjectSearchForm;
import com.example.zoutohanafansite.entity.form.UserSearchForm;
import com.example.zoutohanafansite.entity.project.Project;
import com.example.zoutohanafansite.service.ProjectService;
import com.example.zoutohanafansite.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public String accountList(UserSearchForm form, Model model){
        List<User> users = userService.getAllUsers(form);
        model.addAttribute("users", users);
        model.addAttribute("form", form);
        return "admin/account_list";
    }

    @GetMapping("/account/view")
    public String accountView(@RequestParam String loginId, Model model) {
        User user = userService.getUserById(loginId);
        model.addAttribute("user", user);
        return "admin/account_view";
    }

    @PostMapping("/account/view")
    public String accountStatusUpdate(String status, long id) {
        userService.updateStatus(status, id);
        User user = userService.getUserById(id);
        return "redirect:/admin/account/view?loginId=" + user.getLoginId();
    }

    @GetMapping("/review/edit")
    public String reviewView(@RequestParam long id, Model model) {
        ReviewCard reviewCard = reviewService.getReviewCardById(id);
        model.addAttribute("review", reviewCard);
        return "admin/review_edit";
    }

    @GetMapping("/project/list")
    public String projectList(ProjectSearchForm form, Model model) {
        List<AdminProjectCard> projects = projectService.getAllProjects(form);
        model.addAttribute("projects", projects);
        model.addAttribute("form", form);
        return "admin/project_list";
    }
}
