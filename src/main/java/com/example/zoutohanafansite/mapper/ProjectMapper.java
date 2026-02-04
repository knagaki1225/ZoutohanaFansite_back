package com.example.zoutohanafansite.mapper;

import com.example.zoutohanafansite.entity.admin.project.ProjectCard;
import com.example.zoutohanafansite.entity.form.ProjectSearchForm;
import com.example.zoutohanafansite.entity.project.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ProjectMapper {

    @Select("SELECT * FROM projects WHERE id = #{id}")
    Project getProjectById(long id);

    @Select("""
            SELECT * FROM projects
                WHERE published = true
                    AND url_key = #{urlKey}
                    AND deleted = false
    """)
    Project getProjectByUrlKey(String urlKey);

    // src/main/resources/mapper/ProjectMapper.xmlに移動
    List<ProjectCard> getAllProjects(ProjectSearchForm form);

    @Select("""
            SELECT * FROM projects
                WHERE published = true
                    AND status <> 'AWARD_ANNOUNCEMENT'
                    AND deleted = false
    """)
    List<Project> getAllOngoingProjects();

    @Select("""
            SELECT
                p.*,
                COUNT(r.id) AS review_count
            FROM projects p
            LEFT JOIN reviews r
                ON r.project_id = p.id
            WHERE p.published = true
                AND p.status <> 'AWARD_ANNOUNCEMENT'
                AND p.deleted = false
            GROUP BY p.id
    """)
    List<ProjectCard> getAllOngoingProjectsAdmin();

    @Select("""
            SELECT * FROM projects
                WHERE published = true
                    AND status = 'AWARD_ANNOUNCEMENT'
                    AND deleted = false
    """)
    List<Project> getAllPastProjects();

    @Update("""
        UPDATE projects
        SET deleted = true
        WHERE id = #{id}
    """)
    boolean deleteProjectById(long id);

    @Select("""
        SELECT voting_end_at from projects WHERE url_key = #{urlKey} AND deleted = false
    """)
    LocalDateTime selectVotingEndAt(String urlKey);
}
