package com.devsk.blog.domain.dtos;

import java.time.LocalDate;
import java.util.Set;

import com.devsk.blog.domain.PostStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private AuthorDto author;
    private CategoryDto category;
    private Set<TagResponse> tags;
    private Integer readingTime;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private PostStatus status;
}
