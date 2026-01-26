package com.example.zoutohanafansite.controller;

import com.example.zoutohanafansite.entity.auth.User;
import com.example.zoutohanafansite.entity.enums.ProjectStatus;
import com.example.zoutohanafansite.entity.project.Project;
import com.example.zoutohanafansite.entity.project.ProjectMyPage;
import com.example.zoutohanafansite.entity.review.Review;
import com.example.zoutohanafansite.entity.review.ReviewMyPage;
import com.example.zoutohanafansite.security.CustomUserDetails;
import com.example.zoutohanafansite.service.ProjectService;
import com.example.zoutohanafansite.service.ReviewService;
import com.example.zoutohanafansite.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/mypage")
public class MyPageController {
    private final UserService userService;
    private final ProjectService projectService;
    private final ReviewService reviewService;

    public MyPageController(UserService userService, ProjectService projectService, ReviewService reviewService) {
        this.userService = userService;
        this.projectService = projectService;
        this.reviewService = reviewService;
    }

    @GetMapping
    public String mypage(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        User user = userService.getUserById(customUserDetails.getUsername());
        model.addAttribute("user", user);

        List<Project> ongoingProjects = projectService.getAllOngoingProjects();
        List<ProjectMyPage> myPageProjects = new ArrayList<>();

        for(Project project : ongoingProjects){
            Integer lastDate = projectService.getLastDate(project);
            boolean draft = false;
            if(project.getStatus() == ProjectStatus.DURING_SUBMISSION){
                Long reviewId = reviewService.getDraftId(project.getId(), user.getId());
                if(reviewId != null){
                    draft = true;
                }
            }
            myPageProjects.add(new ProjectMyPage(project, lastDate, draft));
        }

        model.addAttribute("projects", myPageProjects);

        List<Review> reviews = reviewService.getAllReviewsNotDraftByUserId(user.getId());
        List<ReviewMyPage>  myPageReviews = new ArrayList<>();
        for(Review review : reviews){
            Project project = projectService.getProjectById(review.getProjectId());
            String content = review.getReviewContent();
            if(content.length() > 30){
                content = content.substring(0, 30) + "...";
            }
            myPageReviews.add(new ReviewMyPage(review, project));
        }
        model.addAttribute("reviews", myPageReviews);

        return "books/mypage";
    }

    @GetMapping("/review/detail/{reviewId}")
    public String reviewDetail(@PathVariable("reviewId") long reviewId, Model model){
        Review review = reviewService.getReviewById(reviewId);
        model.addAttribute("review", review);
        Project project = projectService.getProjectById(review.getProjectId());
        model.addAttribute("project", project);
        return "/books/book-detail";
    }

}
