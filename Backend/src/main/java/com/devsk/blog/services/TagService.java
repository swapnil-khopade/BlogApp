package com.devsk.blog.services;

import java.util.List;
import java.util.Set;

import com.devsk.blog.domain.entities.Tag;

public interface TagService {
    List<Tag> getTags();
    List<Tag> createTags(Set<String> tagNames);
    void deleteTag(Long id);
    Tag getTagById(Long id);
    List<Tag> getTagbyIds(Set<Long> ids);
}
