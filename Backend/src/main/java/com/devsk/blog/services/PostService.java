package com.devsk.blog.services;

import java.util.List;

import com.devsk.blog.domain.CreatePostRequest;
import com.devsk.blog.domain.UpdatePostRequest;
import com.devsk.blog.domain.entities.Post;
import com.devsk.blog.domain.entities.User;

public interface PostService {
    Post getPost(Long id);
    List<Post> getAllPosts(Long categoryId, Long tagId);
    List<Post> getDraftPosts(User user);
    Post createPost(User user, CreatePostRequest createPostRequest);
    Post updatePost(Long id, UpdatePostRequest updatePostRequest);
    void deletePost(Long id);
}
