package com.example.demo2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Book {

    private final UUID id;
    private final String title;
    private final String author;

    public Book(@JsonProperty("id") UUID id, @JsonProperty("title") String title, @JsonProperty("author")String author){
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public UUID getId(){
        return id;
    }
    public String getTitle(){ return title; }
    public String getAuthor() { return author;}
}
