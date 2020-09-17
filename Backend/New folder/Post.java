package com.example.app.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "post")
public class Post implements Serializable {
    private static final long serialVersionUID = 1; //CHANGE THIS WHEN TABLE IS CREATED

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="body")
    private String body;

    @Column(name="creator")
    private String creator;

    @Column(name="hashtag")
    private String hashtag;

    @Column(name="likes")
    private int likes;

    @Column(name="dislikes")
    private int dislikes;

    @Column(name="score")
    private int score;


    public Post(String title, String body, String creator, String hashtag, int likes, int dislikes) {
        this.title = title;
        this.body = body;
        this.creator = creator;
        this.hashtag = hashtag;
        this.likes = likes;
        this.dislikes = dislikes;
        this.score = likes - dislikes;
    }



}
