package com.example.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Like class
 */
@Entity
@Table(name = "likes")
@EntityListeners(AuditingEntityListener.class)
public class like implements Serializable {

    //DB column to save like id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //DB column to add a reference to the post of the dislike
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private post post;

    //DB column to add a reference to the author of the dislike
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private user user;

    //Constructor to initialize DB columns
    public like(user user, post post, Long id) {
        this.user = user;
        this.post = post;
        this.id = id;
    }

    //Empty constructor to help with GET requests
    public like() {
    }

    //*****************//

    /*************************/
    /** Getters and Setters **/
    /*************************/

    public user getUser() {
        return user;
    }

    public void setUser(user user) {
        this.user = user;
    }

    public post getPost() {
        return post;
    }

    public void setPost(post post) {
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*****************************/
    /** END Getters and Setters **/
    /*****************************/

    //*****************//

}
