package com.example.zoutohanafansite.mapper;

import com.example.zoutohanafansite.entity.post.Post;
import com.example.zoutohanafansite.entity.post.PostTop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PostMapper {

    @Select("""
            SELECT * FROM posts
            WHERE status = 'PUBLIC'              -- 1. ステータスが「公開」であること
              AND posted_at <= CURRENT_TIMESTAMP  -- 2. 投稿日時が現在時刻を過ぎていること
              AND deleted = false
            ORDER BY posted_at DESC;
    """)
    List<Post> selectPublicPosts();

    @Select("""
            SELECT * FROM posts
            WHERE category = #{category}  -- ここに検索したいカテゴリ名を入れる
              AND status = 'PUBLIC'
              AND deleted = false            -- 論理削除されているものを除外する場合
              AND posted_at <= CURRENT_TIMESTAMP
            ORDER BY posted_at DESC;         -- 新しい順に並べる
    """)
    List<Post> selectPublicPostsByCategory(String category);

    @Select("""
            SELECT * FROM posts
            WHERE id = #{id}
            AND status = 'PUBLIC'
            AND deleted = false
            AND posted_at <= CURRENT_TIMESTAMP
    """)
    Post selectPublicPostById(long id);

    @Select("""
            SELECT id, category, title, posted_at FROM posts
            WHERE title LIKE CONCAT('%', #{keyword}, '%')
              AND status = 'PUBLIC'
              AND deleted = false
            ORDER BY posted_at DESC
    """)
    List<PostTop> selectPostsByKeyword(String keyword);
}
