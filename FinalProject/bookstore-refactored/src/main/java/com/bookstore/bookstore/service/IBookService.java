package com.bookstore.bookstore.service;

import java.util.List;
import com.bookstore.bookstore.entity.Book;
import com.bookstore.bookstore.observer.BookObserver;

public interface IBookService {
    void save(Book b);
    List<Book> getAllBook();
    Book getBookById(int id);
    void deleteBookById(int id);
    
    // Observer pattern methods (Subject interface)
    void attach(BookObserver observer);
    void detach(BookObserver observer);
    void notifyObservers(Book book, String action);
}
