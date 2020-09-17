package com.example.app.repo;

import com.example.app.model.comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Comment repository interface that allows the API to communicate with the comment table in the DB
 */

public interface commentRepo extends JpaRepository<comment, Long> {

    //Find a List of comments under a post by postId
    List<comment> findByPostId(Long postId);

    //Find a specific comment under a post with commentId and postId
    Optional<comment> findByIdAndPostId(Long id, Long postId);

    //Find a List of comments under a user by postId
    @Query(value = "SELECT * FROM comment d WHERE d.user_id = :id", nativeQuery = true)
    List<comment> findByUserID(@Param("id") Long userId);

    //Find a comment by commentId
    @Query("select c from comment c where c.id = :id")
    public comment findByID(@Param("id") Long commentId);
}
