package com.example.app.controller;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.exception.UserNotFoundException;
import com.example.app.model.comment;
import com.example.app.repo.commentRepo;
import com.example.app.repo.postRepo;
import com.example.app.repo.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * API class for managing comments on a post
 */

@RestController
@RequestMapping("/api/post")
public class apiControllerComment {

    //Reference to commentRepo interface
    @Autowired
    commentRepo commentRepo;

    //Reference to userRepo interface
    @Autowired
    postRepo postRepo;

    //Reference to userRepo interface
    @Autowired
    userRepo userRepo;


    //*****************//

    /***********************/
    /**** GET Comment ****/
    /**********************/

    /**
     * Get comments of a post
     *
     * @param postId
     * @return List of comments under a post
     */
    @GetMapping("/{postId}/commentsByPostId")
    public List<comment> getAllCommentsByPostId(@PathVariable(value = "postId") Long postId) {
        return commentRepo.findByPostId(postId);
    }

    /**
     * Get all comments by a user
     * Get comments of a specific use
     *
     * @param userId
     * @return List of comments from a specific user
     */
    @GetMapping("/{userId}/commentsByUserId")
    public List<comment> getAllCommentsByUserId(@PathVariable(value = "userId") Long userId) {
        return commentRepo.findByUserID(userId);
    }

    /*********************/
    /** END GET Comment **/
    /*********************/

    //*****************//

    /***********************/
    /**** POST Comments ***/
    /**********************/

    /**
     * Post a comment with userId and postId
     *
     * @param userId
     * @param postId
     * @param comment
     * @return Saved comment to database.
     * @throws UserNotFoundException
     */
    @PostMapping("{userId}/{postId}/comment")
    public comment createComment(@PathVariable(value = "userId") Long userId,
                                 @PathVariable(value = "postId") Long postId,
                                 @Valid @RequestBody comment comment) throws UserNotFoundException {
        if (!userRepo.existsById(userId)) {
            throw new UserNotFoundException(userId);
        } else {
            return postRepo.findById(postId).map(post -> {
                comment.setUser(userRepo.findByID(userId));
                comment.setPost(post);
                return commentRepo.save(comment);
            }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
        }
    }

    /**********************/
    /** END POST Comment **/
    /**********************/

    //*****************//

    /*********************/
    /**** PUT Comments ***/
    /*********************/

    /**
     * Update comment with userId and postId
     *
     * @param userId
     * @param postId
     * @param commentId
     * @param commentRequest
     * @return Saved updated comment to database
     * @throws UserNotFoundException
     */
    @PutMapping("{userId}/{postId}/updateComment/{commentId}")
    public comment updateComment(@PathVariable(value = "userId") Long userId,
                                 @PathVariable(value = "postId") Long postId,
                                 @PathVariable(value = "commentId") Long commentId,
                                 @Valid @RequestBody comment commentRequest) throws UserNotFoundException {
        System.out.println(userRepo.findByUserName("hr24612"));
        if (!userRepo.existsById(userId)) {
            throw new UserNotFoundException(userId);
        } else if (!postRepo.existsById(postId)) {
            throw new ResourceNotFoundException("PostId " + postId + " not found");
        } else {
            return commentRepo.findById(commentId).map(comment -> {
                comment.setText(commentRequest.getText());
                return commentRepo.save(comment);
            }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + "not found"));
        }
    }

    /*********************/
    /** END PUT Comment **/
    /*********************/

    //*****************//

    /*********************/
    /*** DELETE Comment **/
    /*********************/

    /**
     * Delete a comment with userId and postId
     *
     * @param userId
     * @param postId
     * @param commentId
     * @return Deleted comment from database
     * @throws UserNotFoundException
     */
    @DeleteMapping("{userId}/{postId}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "userId") Long userId,
                                           @PathVariable(value = "postId") Long postId,
                                           @PathVariable(value = "commentId") Long commentId) throws UserNotFoundException {

        return commentRepo.findById(commentId).map(comment -> {
            if (comment.getUser().getId().equals(userId) && comment.getPost().getId().equals(postId)) {
                commentRepo.delete(comment);
                return new ResponseEntity<>("Deleted!", HttpStatus.OK);
            } else {
                throw new ResourceNotFoundException("userId: " + userId + " or " + " postId: " + postId + " were incorrect");
            }
        }).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId));
    }

    /************************/
    /** END DELETE Comment **/
    /************************/

    //*****************//

}
