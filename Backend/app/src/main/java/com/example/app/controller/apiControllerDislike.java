package com.example.app.controller;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.exception.UserNotFoundException;
import com.example.app.model.dislike;
import com.example.app.repo.dislikeRepo;
import com.example.app.repo.likeRepo;
import com.example.app.repo.postRepo;
import com.example.app.repo.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * API class for managing dislikes on a post
 */

@RestController
@RequestMapping("/api/post")
public class apiControllerDislike {

    //Reference to postRepo interface
    @Autowired
    postRepo postRepo;

    //Reference to userRepo interface
    @Autowired
    userRepo userRepo;

    //Reference to dislikeRepo interface
    @Autowired
    dislikeRepo dislikeRepo;

    //Reference to likeRepo interface
    @Autowired
    likeRepo likeRepo;

    //*****************//

    /******************/
    /** GET DISLIKES **/
    /******************/

    /**
     * Get dislikes for a post with postId
     *
     * @param postId
     * @return List of dislikes per post
     */
    @GetMapping("/{postId}/dislikesByPostId")
    public List<dislike> getAllDislikesByPostId(@PathVariable(value = "postId") Long postId) throws Exception {
        if (dislikeRepo.findByPostId(postId).size() >= 1) {
            return dislikeRepo.findByPostId(postId);
        } else {
            throw new Exception("Dislikes don't exist");
        }
    }

    /**
     * Get posts liked by a user with userId
     *
     * @param userId
     * @return All disliked objects by a user
     */
    @GetMapping("/{userId}/dislikesByUserId")
    public List<dislike> getAllDislikesByUserId(@PathVariable(value = "userId") Long userId) throws Exception {
        if (dislikeRepo.findByUserId(userId).size() >= 1) {
            return dislikeRepo.findByUserId(userId);
        } else {
            throw new Exception("Dislikes don't exist");
        }
    }

    /**
     * Get dislikeID
     *
     * @param userId
     * @param postId
     * @return the ID of the dislike
     */
    @GetMapping("/{userId}/{postId}/getDislikeId")
    public String getDislikeId(@PathVariable(value = "userId") Long userId, @PathVariable(value = "postId") Long postId) {
        if (dislikeRepo.findDisLikeByPostIdAndUserId(postId, userId) != null) {
            return "{\"like_id\":\"" + dislikeRepo.findDisLikeByPostIdAndUserId(postId, userId).getId() + "\"}";
        } else {
            throw new ResourceNotFoundException("Dislike doesn't exist");
        }
    }

    /**********************/
    /** END GET DISLIKES **/
    /**********************/

    //*****************//

    /********************/
    /** DISLIKES POSTS**/
    /******************/

    //Dislike a post with userId and postId
    @PostMapping("/{userId}/{postId}/dislike")
    public String dislikePost(@PathVariable(value = "userId") Long userId,
                              @PathVariable(value = "postId") Long postId,
                              @Valid dislike dislike) throws Exception {
        if (!userRepo.existsById(userId)) {
            throw new UserNotFoundException(userId);
        } else if (dislikeRepo.findDisLikeByPostIdAndUserId(postId, userId) != null) {
            throw new Exception("You have already disliked this post");
        } else {
            try {
                if (likeRepo.findLikeByPostIdAndUserId(postId, userId) != null) {
                    likeRepo.delete(likeRepo.findLikeByPostIdAndUserId(postId, userId));
                }
            } catch (Exception e) {
            }
            return postRepo.findById(postId).map(post -> {
                dislike.setUser(userRepo.findByID(userId));
                dislike.setPost(post);
                dislikeRepo.save(dislike);
                return "{\"message\":\"Post disliked\"}";
            }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
        }
    }

    /************************/
    /** END DISLIKES POSTS **/
    /************************/

    //***********************//

    /************************/
    /** UN-DISLIKES POSTS **/
    /**********************/

    /**
     * Un-dislike with userId and postId
     *
     * @param userId
     * @param postId
     * @return Un-dislike a post
     * @throws UserNotFoundException
     */
    @DeleteMapping("{userId}/{postId}/unDislike")
    public String unDislike(@PathVariable(value = "userId") Long userId,
                            @PathVariable(value = "postId") Long postId) throws Exception {
        if (dislikeRepo.findDisLikeByPostIdAndUserId(postId, userId) != null) {
            dislikeRepo.delete(dislikeRepo.findDisLikeByPostIdAndUserId(postId, userId));
            return "{\"message\":\"Post undisliked\"}";
        } else {
            throw new Exception("You have already undisliked");
        }
    }

    /***************************/
    /** END UN-DISLIKES POSTS **/
    /***************************/

    //***********************//
}
