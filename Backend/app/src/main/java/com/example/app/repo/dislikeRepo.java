package com.example.app.repo;

import com.example.app.model.dislike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Dislike repository interface that allows the API to communicate with the dislike table in the DB
 */

@Repository
public interface dislikeRepo extends JpaRepository<dislike, Long> {

    //Find a list of dislikes for a user with userId
    @Query(value = "SELECT * FROM dislikes d WHERE d.user_id = :id", nativeQuery = true)
    List<dislike> findByUserId(@Param("id") Long userId);

    //Find a list of dislikes for a post with postId
    @Query(value = "SELECT * FROM dislikes c WHERE c.post_id = :id", nativeQuery = true)
    List<dislike> findByPostId(@Param("id") Long postId);

    //Find dislike from a postId and userId
    @Query("SELECT t FROM dislike t WHERE (post_id = :postId and user_id = :userId)")
    public dislike findDisLikeByPostIdAndUserId(@Param("postId") Long postId, @Param("userId") Long userId);
}
