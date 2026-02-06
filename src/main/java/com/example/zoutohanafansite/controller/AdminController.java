package com.example.zoutohanafansite.controller;

import com.example.zoutohanafansite.entity.admin.project.ProjectCard;
import com.example.zoutohanafansite.entity.auth.User;
import com.example.zoutohanafansite.entity.form.ProjectSearchForm;
import com.example.zoutohanafansite.entity.form.UserSearchForm;
import com.example.zoutohanafansite.entity.nominatedreview.NominatedReview;
import com.example.zoutohanafansite.entity.project.Project;
import com.example.zoutohanafansite.entity.admin.review.ReviewCard;
import com.example.zoutohanafansite.service.NominatedReviewService;
import com.example.zoutohanafansite.service.ProjectService;
import com.example.zoutohanafansite.service.ReviewService;
import com.example.zoutohanafansite.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProjectService projectService;
    private final UserService userService;
    private final ReviewService reviewService;
    private final NominatedReviewService nominatedReviewService;

    public AdminController(ProjectService projectService, UserService userService, ReviewService reviewService, NominatedReviewService nominatedReviewService) {
        this.projectService = projectService;
        this.userService = userService;
        this.reviewService = reviewService;
        this.nominatedReviewService = nominatedReviewService;
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
    public String accountView(@RequestParam(value="loginId", required = false) String loginId, Model model) {
        if (loginId == null || loginId.isEmpty()) {
            return "redirect:/admin/account/list";
        }
        User user = userService.getUserByLoginId(loginId);
        List<ReviewCard> reviews = reviewService.getReviewCardsByUserId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("reviews", reviews);
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

    @GetMapping("/project/view")
    public String projectView(@RequestParam(value="urlKey", required = false) String urlKey, Model model) {
        if (urlKey == null || urlKey.isEmpty()) {
            return "redirect:/admin/project/list";
        }
        Project project = projectService.getAllProjectByUrlKey(urlKey);
        List<NominatedReview> reviews = nominatedReviewService.getByProjectId(project.getId());
        model.addAttribute("project", project);
        model.addAttribute("reviews", reviews);
        return "admin/project_edit";
    }

    @PostMapping("/project/view")
    public String projectUpdate(Project project) {
        projectService.updateProject(project);
        return "redirect:/admin/project/view?urlKey=" + project.getUrlKey();
    }
}
