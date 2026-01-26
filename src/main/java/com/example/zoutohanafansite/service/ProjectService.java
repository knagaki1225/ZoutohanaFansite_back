package com.example.zoutohanafansite.service;

import com.example.zoutohanafansite.entity.project.Project;
import com.example.zoutohanafansite.mapper.ProjectMapper;
import com.example.zoutohanafansite.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project getProjectById(long id) {
        return projectRepository.getProjectById(id);
    }

    public List<Project> getAllOngoingProjects(){
        return projectRepository.getAllOngoingProjects();
    }

    public List<Project> getAllPastProjects(){
        return projectRepository.getAllPastProjects();
    }

    public Integer getLastDate(Project project){
        LocalDateTime targetDate;
        LocalDateTime now = LocalDateTime.now();
        switch (project.getStatus()){
            case BEFORE_SUBMISSION:
                targetDate = project.getSubmissionStartAt();
                break;
            case DURING_SUBMISSION:
                targetDate = project.getSubmissionEndAt();
                break;
            case SECOND_PHASE_VOTING:
                targetDate = project.getVotingEndAt();
                break;
            default:
                return null;
        }
        return (int) ChronoUnit.DAYS.between(now.toLocalDate(), targetDate.toLocalDate());
    }

    public Project getProjectByUrlKey(String urlKey){
        return projectRepository.getProjectByUrlKey(urlKey);
    }
}
