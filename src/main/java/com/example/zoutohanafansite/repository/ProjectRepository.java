package com.example.zoutohanafansite.repository;

import com.example.zoutohanafansite.entity.project.Project;
import com.example.zoutohanafansite.mapper.ProjectMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectRepository {
    private final ProjectMapper projectMapper;

    public ProjectRepository(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    public Project getProjectById(long id) {
        return projectMapper.getProjectById(id);
    }

    public List<Project> getAllOngoingProjects(){
        return projectMapper.getAllOngoingProjects();
    }

    public List<Project> getAllPastProjects(){
        return projectMapper.getAllPastProjects();
    }

    public Project getProjectByUrlKey(String urlKey){
        return projectMapper.getProjectByUrlKey(urlKey);
    }
}
