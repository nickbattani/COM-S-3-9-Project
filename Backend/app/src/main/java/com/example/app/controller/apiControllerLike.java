package com.example.app.controller;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.exception.UserNotFoundException;
import com.example.app.model.like;
import com.example.app.repo.dislikeRepo;
import com.example.app.repo.likeRepo;
import com.example.app.repo.postRepo;
import com.example.app.repo.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * API class for managing likes on a post
 */

@RestController
@RequestMapping("/api/post")
public class apiControllerLike {

    //Reference to postRepo interface
    @Autowired
    postRepo postRepo;

    //Reference to userRepo interface
    @Autowired
    userRepo userRepo;

    //Reference to likeRepo interface
    @Autowired
    likeRepo likeRepo;

    //Reference to dislikeRepo interface
    @Autowired
    dislikeRepo dislikeRepo;
    //****************//

    /****************/
    /** GET LIKES ***/
    /****************/

    /**
     * Get likes for a post with postId
     *
     * @param postId
     * @return Amount of likes for a post ID
     */
    @GetMapping("/{postId}/likesByPostId")
    public List<like> getAllLikesByPostId(@PathVariable(value = "postId") Long postId) throws Exception {
        if (likeRepo.findByPostId(postId).size() >= 1) {
            return likeRepo.findByPostId(postId);
        } else {
            throw new Exception("Likes don't exist");
        }
    }

    /**
     * Get posts liked by a user with userId
     *
     * @param userId
     * @return List of likes by a certain user
     */
    @GetMapping("/{userId}/likesByUserId")
    public List<like> getAllLikesByUserId(@PathVariable(value = "userId") Long userId) throws Exception {
        if (likeRepo.findByUserId(userId).size() >= 1) {
            return likeRepo.findByUserId(userId);
        } else {
            throw new Exception("Likes don't exist");
        }
    }

    /**
     * Get likeId
     *
     * @param userId
     * @param postId
     * @return Get a specific like by its ID
     */
    @GetMapping("/{userId}/{postId}/getLikeId")
    public String getLikeId(@PathVariable(value = "userId") Long userId, @PathVariable(value = "postId") Long postId) {
        if (likeRepo.findLikeByPostIdAndUserId(postId, userId) != null) {
            return "{\"like_id\":\"" + likeRepo.findLikeByPostIdAndUserId(postId, userId).getId() + "\"}";
        } else {
            throw new ResourceNotFoundException("Like doesn't exist");
        }
    }

    /********************/
    /** END GET LIKES ***/
    /********************/

    //*****************//

    /****************/
    /** LIKE Post ***/
    /****************/

    //Like a post with userId and postId
    @PostMapping("/{userId}/{postId}/like")
    public String likePost(@PathVariable(value = "userId") Long userId,
                           @PathVariable(value = "postId") Long postId,
                           @Valid like like) throws Exception {
        if (!userRepo.existsById(userId)) {
            throw new UserNotFoundException(userId);
        } else if (likeRepo.findLikeByPostIdAndUserId(postId, userId) != null) {
            throw new Exception("You have already liked this post");
        } else {
            try {
                if (dislikeRepo.findDisLikeByPostIdAndUserId(postId, userId) != null) {
                    dislikeRepo.delete(dislikeRepo.findDisLikeByPostIdAndUserId(postId, userId));
                }
            } catch (Exception e) {
            }
            return postRepo.findById(postId).map(post -> {
                like.setUser(userRepo.findByID(userId));
                like.setPost(post);
                likeRepo.save(like);
                return "{\"message\":\"Post liked\"}";
            }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
        }
    }

    /********************/
    /** END LIKE POST ***/
    /********************/

    //*****************//

    /*****************/
    /** UNLIKE Post **/
    /****************/

    /**
     * Unlike with userId and postId
     *
     * @param userId
     * @param postId
     * @return Unlike the post (delete from database)
     * @throws UserNotFoundException
     */
    @DeleteMapping("{userId}/{postId}/unLike")
    public String unLike(@PathVariable(value = "userId") Long userId,
                         @PathVariable(value = "postId") Long postId) throws Exception {
        if (likeRepo.findLikeByPostIdAndUserId(postId, userId) != null) {
            likeRepo.delete(likeRepo.findLikeByPostIdAndUserId(postId, userId));
            return "{\"message\":\"Post unliked\"}";
        } else {
            throw new Exception("You have already unliked");
        }
    }

    /***********************/
    /** END UNLIKE POST ****/
    /***********************/

    //**********************//

}
