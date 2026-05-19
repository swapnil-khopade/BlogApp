package com.devsk.blog.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsk.blog.domain.dtos.CreateTagRequest;
import com.devsk.blog.domain.dtos.TagResponse;
import com.devsk.blog.domain.entities.Tag;
import com.devsk.blog.mappers.TagMapper;
import com.devsk.blog.services.TagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags() {
        List<Tag> tags = tagService.getTags();
        List<TagResponse> tagResponses = tags.stream().map(tagMapper::toTagResponse).toList();
        return ResponseEntity.ok(tagResponses);

    }

    @PostMapping
    public ResponseEntity<List<TagResponse>> createTag(@RequestBody CreateTagRequest createTagRequest){

        List<Tag> saveTags = tagService.createTags(createTagRequest.getNames());
        List<TagResponse> createTagResponses = saveTags.stream().map(tag -> tagMapper.toTagResponse(tag)).toList();
        return new ResponseEntity<>(
            createTagResponses,
            HttpStatus.CREATED
        );
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id){
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
