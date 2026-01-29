package com.example.zoutohanafansite.mapper;

import com.example.zoutohanafansite.entity.admin.project.AdminProjectCard;
import com.example.zoutohanafansite.entity.project.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
    List<AdminProjectCard> getAllOngoingProjectsAdmin();

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

    @Update("""
        UPDATE projects
        SET deleted = true
        WHERE id = #{id}
    """)
    boolean deleteProjectById(long id);
}
