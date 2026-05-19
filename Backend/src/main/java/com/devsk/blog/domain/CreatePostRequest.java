package com.devsk.blog.domain;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePostRequest {
    private String title;

    private String content;

    private Long categoryId;

    @Builder.Default
    private Set<Long> tagIds = new HashSet<>();
    
    private PostStatus status;
}
