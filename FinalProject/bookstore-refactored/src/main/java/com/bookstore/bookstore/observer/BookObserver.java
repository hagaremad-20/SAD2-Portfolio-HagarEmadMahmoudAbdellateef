package com.bookstore.bookstore.observer;

import com.bookstore.bookstore.entity.Book;

public interface BookObserver {
    void update(Book book, String action);
}
