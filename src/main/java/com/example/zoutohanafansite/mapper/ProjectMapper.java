package com.example.zoutohanafansite.mapper;

import com.example.zoutohanafansite.entity.project.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProjectMapper {

    @Select("SELECT * FROM projects WHERE id = #{id}")
    Project getProjectById(long id);

    @Select("""
            SELECT * FROM projects
                WHERE published = true
                    AND status <> 'AWARD_ANNOUNCEMENT'
                    AND deleted = false
    """)
    List<Project> getAllOngoingProjects();

    @Select("""
            SELECT * FROM projects
                WHERE published = true
                    AND status = 'AWARD_ANNOUNCEMENT'
                    AND deleted = false
    """)
    List<Project> getAllPastProjects();

    @Select("""
            SELECT * FROM projects
                WHERE published = true
                    AND url_key = #{urlKey}
                    AND deleted = false
    """)
    Project getProjectByUrlKey(String urlKey);
}
