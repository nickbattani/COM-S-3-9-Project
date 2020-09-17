package com.example.app.repo;

import com.example.app.model.like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Like repository interface that allows the API to communicate with the like table in the DB
 */

@Repository
public interface likeRepo extends JpaRepository<like, Long> {

    //Find a list of dislikes for a user with userId
    @Query(value = "SELECT * FROM likes d WHERE d.user_id = :userId", nativeQuery = true)
    public List<like> findByUserId(@Param("userId") Long userId);

    //Find a list of dislikes for a post with postId
    @Query(value = "SELECT * FROM likes c WHERE c.post_id = :postId", nativeQuery = true)
    public List<like> findByPostId(@Param("postId") Long postId);

    //Find like from a postId and userId
    @Query("SELECT t FROM like t WHERE (post_id = :postId and user_id = :userId)")
    public like findLikeByPostIdAndUserId(@Param("postId") Long postId, @Param("userId") Long userId);
}
