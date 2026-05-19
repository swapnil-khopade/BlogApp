package com.devsk.blog.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsk.blog.domain.CreatePostRequest;
import com.devsk.blog.domain.PostStatus;
import com.devsk.blog.domain.UpdatePostRequest;
import com.devsk.blog.domain.entities.Category;
import com.devsk.blog.domain.entities.Post;
import com.devsk.blog.domain.entities.Tag;
import com.devsk.blog.domain.entities.User;
import com.devsk.blog.repositories.PostRepository;
import com.devsk.blog.services.CategoryService;
import com.devsk.blog.services.PostService;
import com.devsk.blog.services.TagService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;

    private static final int WORD_PER_MINUTE = 200;
    
    
    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPosts(Long categoryId, Long tagId) {
        if (categoryId != null && tagId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndCategoryAndTagsContaining(
                PostStatus.PUBLISHED, 
                category, 
                tag
            );
        }
        if (categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            return postRepository.findAllByStatusAndCategory(
                PostStatus.PUBLISHED, category);
        }
        if (tagId != null) {
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndTagsContaining(
                PostStatus.PUBLISHED, tag);
        }

        return postRepository.findAllByStatus(PostStatus.PUBLISHED);
    }


    @Override
    public List<Post> getDraftPosts(User user) {
        return postRepository.findAllByAuthorAndStatus(user, PostStatus.DRAFT);
        
    }


    @Override
    @Transactional
    public Post createPost(User user, CreatePostRequest createPostRequest) {
        Post  newPost = new Post();
        newPost.setTitle(createPostRequest.getTitle());
        newPost.setContent(createPostRequest.getContent());
        newPost.setStatus(createPostRequest.getStatus());
        newPost.setAuthor(user);
        newPost.setReadingTime(calculateReadingTime(createPostRequest.getContent()));

        Category category = categoryService.getCategoryById(createPostRequest.getCategoryId());
        newPost.setCategory(category);

        Set<Long> tagIds = createPostRequest.getTagIds();
        List<Tag> tags = tagService.getTagbyIds(tagIds);
        newPost.setTags(new HashSet<>(tags));

        return postRepository.save(newPost);
    }

    private Integer calculateReadingTime(String content){
        if (content == null || content.isEmpty()) {
            return 0;
        }

        int wordCount = content.trim().split("\\s+").length;
        return (int) Math.ceil((double) wordCount / WORD_PER_MINUTE);
    }


    @Override
    @Transactional
    public Post updatePost(Long id, UpdatePostRequest updatePostRequest) {
        Post existingPost = postRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Post does not exist with ID "+id));
        
        existingPost.setTitle(updatePostRequest.getTitle());
        String postContent = updatePostRequest.getContent();
        existingPost.setContent(postContent);
        existingPost.setStatus(updatePostRequest.getStatus());
        existingPost.setReadingTime(calculateReadingTime(postContent));

        Long updatePostRequestCategoryId = updatePostRequest.getCategoryId();
        if (!existingPost.getCategory().getId().equals(updatePostRequestCategoryId)) {
            Category newCategory = categoryService.getCategoryById(updatePostRequestCategoryId);
            existingPost.setCategory(newCategory);
        }
        Set<Long> existingTagIds = existingPost.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
        Set<Long> updatePostRequestTagIds = updatePostRequest.getTagIds();
        if (!existingTagIds.equals(updatePostRequestTagIds)) {
            List<Tag> newTags = tagService.getTagbyIds(updatePostRequestTagIds);
            existingPost.setTags(new HashSet<>(newTags));
        }
        return postRepository.save(existingPost);

    }


    @Override
    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not exist with ID "+id));
    }


    @Override
    public void deletePost(Long id) {
        Post post = getPost(id);
        postRepository.delete(post);
    }

}
