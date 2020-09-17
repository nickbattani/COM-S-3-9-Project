package com.example.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * Comment class
 */

@Entity
@Table(name = "comment")
public class comment extends audit {

    //DB column to save comment id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //DB column to save comment text
    @NotBlank
    @Lob
    private String text;

    //DB column to add a reference to the post of the comment
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private post post;

    //DB column to add a reference to the author of the comment
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private user user;

    //Constructor to initialize the DB columns
    public comment(Long id, String text, post post, user user) {
        this.id = id;
        this.text = text;
        this.post = post;
        this.user = user;
    }

    //Empty constructor to help with GET requests
    public comment() {
    }

    //*****************//

    /*************************/
    /** Getters and Setters **/
    /*************************/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public post getPost() {
        return post;
    }

    public void setPost(post post) {
        this.post = post;
    }

    public user getUser() {
        return user;
    }

    public void setUser(user user) {
        this.user = user;
    }

    /*****************************/
    /** END Getters and Setters **/
    /*****************************/

    //*****************//

}
