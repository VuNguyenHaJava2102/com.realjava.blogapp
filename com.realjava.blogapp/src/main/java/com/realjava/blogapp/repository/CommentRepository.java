package com.realjava.blogapp.repository;

import com.realjava.blogapp.entity.Comment;
import com.realjava.blogapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByPost(Post post);

    @Query("DELETE FROM Comment c where c.id = ?1")
    @Modifying
    void deleteByCommentId(int id);

}
