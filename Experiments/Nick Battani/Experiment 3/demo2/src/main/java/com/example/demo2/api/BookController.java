package com.example.demo2.api;

import com.example.demo2.model.Book;
import com.example.demo2.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/book")
@RestController
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping
    public void addBook(@RequestBody Book book) {
        bookService.addBook(book);
    }

    @GetMapping
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @DeleteMapping(path ="{id}")
    public void deleteBookById(@PathVariable("id") UUID id){
        bookService.deleteBook(id);
    }

    @PutMapping(path="{id}")
    public void updateBook(@PathVariable("id") UUID id, @Valid @NonNull @RequestBody Book bookToUpdate){
        bookService.updateBook(id, bookToUpdate);
    }

}
