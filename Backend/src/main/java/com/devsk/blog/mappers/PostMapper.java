package com.devsk.blog.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.devsk.blog.domain.CreatePostRequest;
import com.devsk.blog.domain.UpdatePostRequest;
import com.devsk.blog.domain.dtos.CreatePostRequestDto;
import com.devsk.blog.domain.dtos.PostDto;
import com.devsk.blog.domain.dtos.UpdatePostRequestDto;
import com.devsk.blog.domain.entities.Post;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "author", source = "author")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags", source = "tags")
    PostDto toDto(Post post);

    CreatePostRequest toCreatePostRequest(CreatePostRequestDto dto);

    UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto dto);
}
