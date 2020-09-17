package com.example.app.repo;

import com.example.app.model.post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Post repository interface that allows the API to communicate with the post table in the DB
 */

@Repository
public interface postRepo extends JpaRepository<post, Long> {

    //Find a list of posts for a user with userId
    List<post> findByUserId(Long userId);

    //Find a user by id
    @Query("select c from post c where c.id = :id")
    public post findByID(@Param("id") Long postId);

    //Find a post with userId and postId
    Optional<post> findByIdAndUserId(Long id, Long userId);
}
