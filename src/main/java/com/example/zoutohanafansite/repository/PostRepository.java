package com.example.zoutohanafansite.repository;

import com.example.zoutohanafansite.entity.post.Post;
import com.example.zoutohanafansite.entity.post.PostTop;
import com.example.zoutohanafansite.mapper.PostMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepository {
    private final PostMapper postMapper;

    public PostRepository(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    public List<Post> selectPublicPosts() {
        return postMapper.selectPublicPosts();
    }

    public List<Post> selectPublicPostsByCategory(String category){
        return postMapper.selectPublicPostsByCategory(category);
    }

    public Post selectPublicPostById(long id){
        return postMapper.selectPublicPostById(id);
    }

    public List<PostTop> selectPostsByKeyword(String keyword){
        return postMapper.selectPostsByKeyword(keyword);
    }

}
