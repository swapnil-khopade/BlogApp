package com.devsk.blog.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsk.blog.domain.CreatePostRequest;
import com.devsk.blog.domain.UpdatePostRequest;
import com.devsk.blog.domain.dtos.CreatePostRequestDto;
import com.devsk.blog.domain.dtos.PostDto;
import com.devsk.blog.domain.dtos.UpdatePostRequestDto;
import com.devsk.blog.domain.entities.Post;
import com.devsk.blog.domain.entities.User;
import com.devsk.blog.mappers.PostMapper;
import com.devsk.blog.services.PostService;
import com.devsk.blog.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
        @RequestParam(required = false) Long categoryId,
        @RequestParam(required = false) Long tagId
    ){
        List<Post> posts = postService.getAllPosts(categoryId, tagId);
        List<PostDto> postDtos = posts.stream().map(postMapper::toDto).toList(); 
        return ResponseEntity.ok(postDtos);
    }

    @GetMapping("/drafts")
    public  ResponseEntity<List<PostDto>> getDrafts(@RequestAttribute Long userId){
        User loggedInUser = userService.getUserById(userId);
        List<Post> drafPosts = postService.getDraftPosts(loggedInUser);
        List<PostDto> postDtos = drafPosts.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(postDtos);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(
        @Valid @RequestBody CreatePostRequestDto createPostRequestDto,
        @RequestAttribute Long userId
    ){
            User loggedInUser = userService.getUserById(userId);
            CreatePostRequest createPostRequest = postMapper.toCreatePostRequest(createPostRequestDto);
            Post createdPost = postService.createPost(loggedInUser, createPostRequest);
            PostDto createdPostDto = postMapper.toDto(createdPost);
            return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
    }


    @PutMapping(path = "/{id}")
    public ResponseEntity<PostDto> updatePost(
        @PathVariable Long id,
        @Valid @RequestBody  UpdatePostRequestDto updatePostRequestDto 
    ){

            UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDto);
            Post updatedPost = postService.updatePost(id, updatePostRequest);
            PostDto updatedPostdDto = postMapper.toDto(updatedPost);
            return ResponseEntity.ok(updatedPostdDto);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PostDto> getPost(
        @PathVariable Long id
    ){
        Post post = postService.getPost(id);
        PostDto postDto = postMapper.toDto(post);
        return ResponseEntity.ok(postDto);
    } 

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
        @PathVariable Long id
    ){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
    
}
