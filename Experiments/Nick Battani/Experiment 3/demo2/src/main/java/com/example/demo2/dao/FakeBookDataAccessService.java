package com.example.demo2.dao;

import com.example.demo2.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakeBookDataAccessService implements BookDao {

    private static List<Book> DB = new ArrayList<>();

    @Override
    public int insertBook(UUID id, Book book){
        DB.add(new Book(id, book.getTitle(), book.getAuthor()));
        return 1;
    }
    @Override
    public List<Book> selectAllBooks(){
        return DB;
    }

    @Override
    public Optional<Book> selectBookByID(UUID id) {
        return DB.stream().filter(Book -> Book.getId().equals(id)).findFirst();
    }

    @Override
    public int deleteBookByID(UUID id) {
        Optional<Book> bookMaybe = selectBookByID(id);
        if (bookMaybe.isPresent()) {
            DB.remove(bookMaybe.get());
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int updateBookByID(UUID id, Book updatedBook) {
        return selectBookByID(id).map(p -> {
            int indexOfBookToUpdate= DB.indexOf(p);
            if(indexOfBookToUpdate >= 0){
                DB.set(indexOfBookToUpdate, new Book(id, updatedBook.getTitle(),updatedBook.getAuthor()));
                return 1;
            }
            return 0;
        }).orElse(0);
    }

}
