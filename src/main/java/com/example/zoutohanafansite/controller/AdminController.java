package com.example.zoutohanafansite.controller;

import com.example.zoutohanafansite.entity.admin.project.ProjectCard;
import com.example.zoutohanafansite.entity.admin.review.NominatedReviewCard;
import com.example.zoutohanafansite.entity.auth.User;
import com.example.zoutohanafansite.entity.enums.ProjectStatus;
import com.example.zoutohanafansite.entity.form.AdminNotificationSendForm;
import com.example.zoutohanafansite.entity.form.AdminNotificationTemplateForm;
import com.example.zoutohanafansite.entity.form.ProjectSearchForm;
import com.example.zoutohanafansite.entity.form.UserSearchForm;
import com.example.zoutohanafansite.entity.notificationtemplate.NotificationTemplate;
import com.example.zoutohanafansite.entity.project.Project;
import com.example.zoutohanafansite.entity.admin.review.ReviewCard;
import com.example.zoutohanafansite.entity.review.Review;
import com.example.zoutohanafansite.security.CustomAdminUserDetails;
import com.example.zoutohanafansite.service.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private final ReviewService reviewService;
    private final NotificationTemplateService notificationTemplateService;
    private final NotificationService notificationService;

    public AdminController(ProjectService projectService, UserService userService, ReviewService reviewService, NotificationTemplateService notificationTemplateService, NotificationService notificationService) {
        this.projectService = projectService;
        this.userService = userService;
        this.reviewService = reviewService;
        this.notificationTemplateService = notificationTemplateService;
        this.notificationService = notificationService;
    }

    @GetMapping("/dash")
    public String admin(Model model){
//        List<Project> projects = projectService.getAllOngoingProjects();
//        List<AdminDashProject> adminDashProjects = new ArrayList<>();
//        for(Project project : projects){
//            adminDashProjects.add(new AdminDashProject(project));
//        }
//        model.addAttribute("projects",adminDashProjects);

        List<ProjectCard> projects = projectService.getAllOngoingProjectsAdmin();
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
        List<ProjectCard> projects = projectService.getAllProjects(form);
        model.addAttribute("projects", projects);
        model.addAttribute("form", form);
        return "admin/project_list";
    }

    @GetMapping("/project/view")
    public String projectView(@RequestParam(value="urlKey", required = false) String urlKey, Model model) {
        if (urlKey == null || urlKey.isEmpty()) {
            return "redirect:/admin/project/list";
        }
        Project project = projectService.getProjectByUrlKey(urlKey);
        List<NominatedReviewCard> reviews = reviewService.getNominatedReviewCardByProjectId(project.getId());
        model.addAttribute("project", project);
        model.addAttribute("reviews", reviews);
        return "admin/project_view";
    }

    @GetMapping("/notification/template")
    public String notificationTemplateList(Model model, @RequestParam(defaultValue = "") String s){
        List<NotificationTemplate> notificationTemplates;
        if(s.isEmpty()){
            notificationTemplates = notificationTemplateService.selectAllNotificationTemplate();
        }else{
            notificationTemplates = notificationTemplateService.selectNotificationTemplateByKeyword(s);
        }

        model.addAttribute("notificationTemplates", notificationTemplates);
        return "/admin/notification_template_list";
    }

    @GetMapping("/notification/template/create")
    public String notificationTemplateCreate(Model model){
        AdminNotificationTemplateForm adminNotificationTemplateForm = new AdminNotificationTemplateForm();
        model.addAttribute("adminNotificationTemplateForm", adminNotificationTemplateForm);
        return "/admin/notification_template_create";
    }

    @PostMapping("/notification/template/create")
    public String notificationTemplateGet(AdminNotificationTemplateForm form, RedirectAttributes redirectAttributes){
        int i = 1;
        redirectAttributes.addFlashAttribute("form", form);
        i++;
        return "redirect:/admin/notification/template/create/confirm";
    }

    @GetMapping("/notification/template/create/confirm")
    public String notificationTemplateCreateConfirm(){
        return "/admin/notification_template_create_confirm";
    }

    @PostMapping("/notification/template/create/confirm")
    public String notificationTemplateInsert(AdminNotificationTemplateForm form){
        NotificationTemplate notificationTemplate = new NotificationTemplate();
        notificationTemplate.setName(form.getTemplateName());
        notificationTemplate.setTitle(form.getTitle());
        notificationTemplate.setContent(form.getContent());
        notificationTemplateService.insertNotificationTemplate(notificationTemplate);
        return "redirect:/admin/notification/template";
    }

    @GetMapping("/notification/template/edit/{id}")
    public String notificationTemplateEdit(@PathVariable long id, Model model){
        NotificationTemplate notificationTemplate = notificationTemplateService.selectNotificationTemplateById(id);
        AdminNotificationTemplateForm form =  new AdminNotificationTemplateForm();
        form.setTemplateName(notificationTemplate.getName());
        form.setTitle(notificationTemplate.getTitle());
        form.setContent(notificationTemplate.getContent());
        model.addAttribute("form", form);
        model.addAttribute("templateId", id);
        return "admin/notification_template_edit";
    }

    @PostMapping("/notification/template/edit/{id}")
    public String notificationTemplateEditPost(AdminNotificationTemplateForm form, RedirectAttributes redirectAttributes, @PathVariable long id){
        redirectAttributes.addFlashAttribute("form", form);
        NotificationTemplate notificationTemplate = notificationTemplateService.selectNotificationTemplateById(id);
        return "redirect:/admin/notification/template/edit/confirm/" + notificationTemplate.getId();
    }

    @GetMapping("/notification/template/edit/confirm/{id}")
    public String notificationTemplateEditConfirm(@PathVariable long id, Model model){
        model.addAttribute("id", id);
        return "/admin/notification_template_edit_confirm";
    }

    @PostMapping("/notification/template/edit/confirm/{id}")
    public String notificationTemplateUpdate(AdminNotificationTemplateForm form, @PathVariable long id){
        notificationTemplateService.update(form,id);
        return "redirect:/admin/notification/template";
    }

    @PostMapping("/notification/template/delete/{id}")
    public String deleteNotificationTemplate(@PathVariable long id){
        notificationTemplateService.deleteNotificationTemplate(id);
        return "redirect:/admin/notification/template";
    }

    @GetMapping("/notification/send/{id}")
    public String notificationSend(@PathVariable long id, Model model){
        Review review = reviewService.getReviewById(id);
        model.addAttribute("review", review);

        AdminNotificationSendForm adminNotificationSendForm = new AdminNotificationSendForm();
        model.addAttribute("form", adminNotificationSendForm);

        return "/admin/notification";
    }

    @PostMapping("/notification/send/{id}")
    public String notificationSendPost(@PathVariable long id, Model model, AdminNotificationSendForm form, @AuthenticationPrincipal CustomAdminUserDetails user){
        Review review = reviewService.getReviewById(id);
        notificationService.insertNotificationByForm(form, id, user.getUserId());

        return "redirect:/admin/notification/template";
    }
}
