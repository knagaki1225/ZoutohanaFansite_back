package com.example.zoutohanafansite.service;

import com.example.zoutohanafansite.entity.admin.project.AdminProjectCard;
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

    /**
     * 指定したidのProjectを取得
     *
     * @param id     取得するprojectのid
     *
     * @return Project
     */
    public Project getProjectById(long id) {
        return projectRepository.getProjectById(id);
    }

    /**
     * 開催中のProjectを全件取得
     *
     * @return List<Project>
     */
    public List<Project> getAllOngoingProjects(){
        return projectRepository.getAllOngoingProjects();
    }

    /**
     * 開催中のProjectを全件取得(管理者画面の企画カード用)
     *
     * @return List<AdminProjectCard>
     */
    public List<AdminProjectCard> getAllOngoingProjectsAdmin(){
        return projectRepository.getAllOngoingProjectsAdmin();
    }

    /**
     * すべてのProjectを取得
     *
     * @return List<Project>
     */
    public List<Project> getAllPastProjects(){
        return projectRepository.getAllPastProjects();
    }

    /**
     * 次のステータスまであと何日か取得
     *
     * @param project 情報が欲しいProject
     * @return List<Project>
     */
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

    /**
     * urlKeyを指定してProjectを取得
     *
     * @param urlKey 指定するurlKey
     * @return Project
     */
    public Project getProjectByUrlKey(String urlKey){
        return projectRepository.getProjectByUrlKey(urlKey);
    }

    /**
     * 指定したidのProjectを削除
     *
     * @param id     削除するprojectのid
     *
     * @return boolean
     */
    public boolean deleteProjectById(long id) {
        return projectRepository.deleteProjectById(id);
    }
}
