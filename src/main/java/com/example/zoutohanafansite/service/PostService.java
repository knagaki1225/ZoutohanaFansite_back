package com.example.zoutohanafansite.service;

import com.example.zoutohanafansite.entity.enums.PostCategory;
import com.example.zoutohanafansite.entity.pagination.PaginationInfo;
import com.example.zoutohanafansite.entity.pagination.PaginationView;
import com.example.zoutohanafansite.entity.post.Post;
import com.example.zoutohanafansite.entity.post.PostPagination;
import com.example.zoutohanafansite.entity.post.PostTop;
import com.example.zoutohanafansite.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PaginationService paginationService;

    public PostService(PostRepository postRepository, PaginationService paginationService) {
        this.postRepository = postRepository;
        this.paginationService = paginationService;
    }

    public List<Post> getPublicPosts(){
        return postRepository.selectPublicPosts();
    }

    public List<PostTop> getNew(){
        List<PostTop> result = new ArrayList<PostTop>();
        List<Post> posts = getPublicPosts();
        for(Post post : posts){
            result.add(new PostTop(post));
        }
        return result;
    }

    public List<Post> getPostByCategory(String category){
        return postRepository.selectPublicPostsByCategory(category);
    }

    public List<PostTop> getPostTopByCategory(String category){
        List<Post> posts = getPostByCategory(category);
        List<PostTop> result = new ArrayList<>();
        for(Post post : posts){
            result.add(new PostTop(post));
        }
        return result;
    }

    public PostPagination getPostPagination(String category, int page){
        List<PostTop> posts;
        if(category.equals("new")){
            posts = getNew();
        }else{
            String searchStr = category.toUpperCase();

            try{
                PostCategory.valueOf(searchStr);
            }catch(IllegalArgumentException e){
                // エラー
            }

            posts = getPostTopByCategory(searchStr);
        }

        if(posts.isEmpty()){
            return null;
        }

        List<PostTop> result = new ArrayList<>();
        PaginationView paginationView = paginationService.getPaginationView(page, posts.size(), 6);
        PaginationInfo paginationInfo = paginationView.getPaginationInfo();

        for(int i = paginationView.getStartNum(); i < paginationView.getEndNum(); i++){
            PostTop post = posts.get(i);
            result.add(post);
        }

        return new PostPagination(paginationInfo, result);
    }

    public Post getPublicPostById(long id){
        return postRepository.selectPublicPostById(id);
    }

    public List<PostTop> getPostTopsByKeyword(String keyword){
        return postRepository.selectPostsByKeyword(keyword);
    }
}
