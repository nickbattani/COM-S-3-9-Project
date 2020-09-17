package com.example.demo2.dao;



import com.example.demo2.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookDao {

    int insertBook(UUID id, Book book);

    default int insertBook(Book book){
        UUID id = UUID.randomUUID();
        return insertBook(id, book);
    }

    List<Book> selectAllBooks();

    Optional<Book> selectBookByID(UUID id);

    int deleteBookByID(UUID id);

    int updateBookByID(UUID id, Book book);


}