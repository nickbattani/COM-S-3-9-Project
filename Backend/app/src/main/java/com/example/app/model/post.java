package com.example.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * Post class
 */

@Entity
@Table(name = "post")
@EntityListeners(AuditingEntityListener.class)
public class post extends audit {

    //DB column to save post's id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //DB column to save post's hashTag
    @NotBlank
    private String hashTag;

    @NotBlank
    private String title;

    //DB column to save post's body
    @NotBlank
    @Lob
    private String body;

    //DB column to add a reference to the author of the post
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private user user;

    //Constructor1 to initialize DB columns
    public post(Long id, String hashTag, String body, String title, user user) {
        this.id = id;
        this.hashTag = hashTag;
        this.title = title;
        this.body = body;
        this.user = user;
    }

    //Empty constructor to help with GET requests
    public post() {
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

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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
