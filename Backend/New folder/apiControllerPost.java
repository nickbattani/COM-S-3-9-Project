package com.example.app.controller;

import com.example.app.model.Post;
import com.example.app.repo.postRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class apiControllerPost {

    @Autowired
    postRepo repository;

    //Need to figure out a way to not request literally everything to add a post
    @PostMapping
    public void addPost(@RequestParam String title, String body, String creator, String hashtag, int likes, int dislikes){
        Post newPost = new Post(title, body, creator, hashtag,likes,dislikes);
        //figure out how to save to repo
        //repository.save(newPost);
    }

    //Need to add functionality to receive posts
    @GetMapping
    public void getPost(){

    }

    @GetMapping
    public void getAllPost(){

    }

    //I assume I have to create a get request for sorting by hashtags, new, and score

    @GetMapping
    public void getPostsByHashtag(@RequestParam("hashtag") String hashtag){
        Post allPosts = new Post("","","",hashtag,0,0);

        //help
//        for(Post cust: repository.findByHashtag(hashtag)){
//            allPosts += cust.toPost();
//        }
    }

    @GetMapping
    public void getPostByNew(){

    }

    @GetMapping
    public void getPostsByHighestScore(){

    }

    //Delete a post
    @DeleteMapping
    public void deletePost(){

    }

    //Update a post (probably only going to let them update the body of the post?
    @PutMapping
    public void updatePost(){

    }

}
