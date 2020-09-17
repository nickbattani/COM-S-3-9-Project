package com.example.demo2.service;



import com.example.demo2.dao.BookDao;
import com.example.demo2.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    private final BookDao bookDao;

    @Autowired
    public BookService(@Qualifier("fakeDao") BookDao bookDao){
        this.bookDao = bookDao;
    }
    public int addBook(Book book){
        return bookDao.insertBook(book);
    }
    public List<Book> getAllBooks(){
        return bookDao.selectAllBooks();
    }
    public Optional<Book> getBookById(UUID id){ return bookDao.selectBookByID(id); }
    public int deleteBook(UUID id){ return bookDao.deleteBookByID(id); }
    public int updateBook(UUID id, Book book){ return bookDao.updateBookByID(id, book); }

}