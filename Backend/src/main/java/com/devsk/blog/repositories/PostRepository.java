package com.devsk.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsk.blog.domain.PostStatus;
import com.devsk.blog.domain.entities.Category;
import com.devsk.blog.domain.entities.Post;
import com.devsk.blog.domain.entities.Tag;
import com.devsk.blog.domain.entities.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
    List<Post> findAllByStatusAndCategoryAndTagsContaining(PostStatus status, Category category, Tag tag);
    List<Post> findAllByStatusAndCategory(PostStatus status, Category category);
    List<Post> findAllByStatusAndTagsContaining(PostStatus status, Tag tag);
    List<Post> findAllByStatus(PostStatus status);
    List<Post> findAllByAuthorAndStatus(User user, PostStatus status);
}
